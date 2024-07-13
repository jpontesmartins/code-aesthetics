package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PublicMethodAnnotationsCheck extends BaseCheck {

    private Set<String> classSuffixes = new HashSet<>();
    private Set<String> requiredAnnotations = new HashSet<>();

    @Override
    public void visitToken(DetailAST astNode) {
        String className = astNode.findFirstToken(TokenTypes.IDENT).getText();

        // nome da classe tem um dos sufixos configurados
        if (classSuffixes.stream().anyMatch(className::endsWith)) {

            // bloco de código
            DetailAST codeBlock = astNode.findFirstToken(TokenTypes.OBJBLOCK);
                for (DetailAST child = codeBlock.getFirstChild(); child != null; child = child.getNextSibling()) {
                    
                    // é uma decaração de um método
                    if (child.getType() == TokenTypes.METHOD_DEF) {
                        boolean isPublic = false;
                        Set<String> foundAnnotations = new HashSet<>();

                        // pego o modificador (public, private, protected)
                        DetailAST modifiers = child.findFirstToken(TokenTypes.MODIFIERS);
                        for (DetailAST mod = modifiers.getFirstChild(); mod != null; mod = mod.getNextSibling()) {
                            // verifico se é public
                            if (mod.getType() == TokenTypes.LITERAL_PUBLIC) {
                                isPublic = true;
                                break;
                            }
                        }

                        // se o método for public
                        if (isPublic) {
                            // pego as annotacoes
                            DetailAST annotations = child.findFirstToken(TokenTypes.MODIFIERS);
                            for (DetailAST annotation = annotations.getFirstChild(); annotation != null; annotation = annotation.getNextSibling()) {
                                // pego as annotations do metodo
                                if (annotation.getType() == TokenTypes.ANNOTATION) {
                                    DetailAST annotationIdent = annotation.findFirstToken(TokenTypes.IDENT);
                                    foundAnnotations.add(annotationIdent.getText());
                                }
                            }
                
                            // pego a lsita das annotations configuradas
                            for (String requiredAnnotation : requiredAnnotations) {
                                // se a annotation configurada não estiver na listagem
                                if (!foundAnnotations.contains(requiredAnnotation)) {
                                    // registra log 
                                    log(child.getLineNo(), "method.missing.annotation", child.findFirstToken(TokenTypes.IDENT).getText(), requiredAnnotation);
                                }
                            }
                        }
                    }
                }
        }

    }

    public void setClassSuffixes(String[] suffixes) {
        this.classSuffixes = new HashSet<>(Arrays.asList(suffixes));
    }

    public void setRequiredAnnotations(String[] annotations) {
        this.requiredAnnotations = new HashSet<>(Arrays.asList(annotations));
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

}