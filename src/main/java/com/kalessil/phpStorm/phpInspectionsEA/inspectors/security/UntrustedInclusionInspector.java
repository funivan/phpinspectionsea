package com.kalessil.phpStorm.phpInspectionsEA.inspectors.security;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.Include;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import com.kalessil.phpStorm.phpInspectionsEA.utils.ExpressionSemanticUtil;
import org.jetbrains.annotations.NotNull;

/*
 * This file is part of the Php Inspections (EA Extended) package.
 *
 * (c) Vladimir Reznichenko <kalessil@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

public class UntrustedInclusionInspector extends BasePhpInspection {
    private static final String strProblemDescription = "This relies on include_path and not guaranteed to load the right file. Concatenate with __DIR__ or use namespaces + class loading instead.";

    @NotNull
    public String getShortName() {
        return "UntrustedInclusionInspection";
    }

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            public void visitPhpInclude(Include include) {
                if (null == include.getArgument()) {
                    return;
                }

                StringLiteralExpression file = ExpressionSemanticUtil.resolveAsStringLiteral(include.getArgument());
                if (null != file) {
                    holder.registerProblem(include, strProblemDescription, ProblemHighlightType.GENERIC_ERROR);
                }
            }
        };
    }
}