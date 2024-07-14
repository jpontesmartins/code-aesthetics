package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PublicMethodAnnotationsCheck extends BaseCheck {

    private static final String METHOD_MISSING_ANNOTATION = "method.missing.annotation";
    private Set<String> classSuffixes = new HashSet<>();
    private Set<String> requiredAnnotations = new HashSet<>();

    @Override
    public void visitToken(DetailAST node) {
        String className = node.findFirstToken(TokenTypes.IDENT).getText();

        if (classSuffixes.stream().anyMatch(className::endsWith)) {
            DetailAST nodeCodeBlock = node.findFirstToken(TokenTypes.OBJBLOCK);
            for (DetailAST child = nodeCodeBlock.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.METHOD_DEF) {
                    Set<String> foundAnnotations = new HashSet<>();
                    if (isPublicModifier(child)) {
                        searchForAnnotations(child, foundAnnotations);
                        checkIfAnnotationIsPresent(child, foundAnnotations);
                    }
                }
            }
        }

    }

    private void checkIfAnnotationIsPresent(DetailAST node, Set<String> foundAnnotations) {
        for (String requiredAnnotation : requiredAnnotations) {
            if (!foundAnnotations.contains(requiredAnnotation)) {
                log(node.getLineNo(),
                    METHOD_MISSING_ANNOTATION,
                    node.findFirstToken(TokenTypes.IDENT).getText(), requiredAnnotation);
            }
        }
    }

    private void searchForAnnotations(DetailAST node, Set<String> foundAnnotations) {
        DetailAST nodeModifier = node.findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST annotation = nodeModifier.getFirstChild(); annotation != null; annotation = annotation
                .getNextSibling()) {
            if (annotation.getType() == TokenTypes.ANNOTATION) {
                DetailAST annotationIdent = annotation.findFirstToken(TokenTypes.IDENT);
                foundAnnotations.add(annotationIdent.getText());
            }
        }
    }

    private boolean isPublicModifier(DetailAST node) {
        DetailAST nodeModifier = node.findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST modifier = nodeModifier.getFirstChild(); modifier != null; modifier = modifier.getNextSibling()) {
            if (modifier.getType() == TokenTypes.LITERAL_PUBLIC) {
                return true;
            }
        }
        return false;
    }

    public void setClassSuffixes(String[] suffixes) {
        this.classSuffixes = new HashSet<>(Arrays.asList(suffixes));
    }

    public void setRequiredAnnotations(String[] annotations) {
        this.requiredAnnotations = new HashSet<>(Arrays.asList(annotations));
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

}