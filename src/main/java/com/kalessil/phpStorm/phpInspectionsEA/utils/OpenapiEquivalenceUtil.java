package com.kalessil.phpStorm.phpInspectionsEA.utils;

import com.intellij.codeInsight.PsiEquivalenceUtil;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.Variable;
import org.jetbrains.annotations.NotNull;

/*
 * This file is part of the Php Inspections (EA Extended) package.
 *
 * (c) Vladimir Reznichenko <kalessil@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

final public class OpenapiEquivalenceUtil {
    public static boolean areEqual(@NotNull PsiElement first, @NotNull PsiElement second) {
        boolean result = false;
        try {
            if (first.getClass() == second.getClass()) {
                if (first instanceof Variable && second instanceof Variable) {
                    /* parser specific: "{$variable}" includes '{}' into variable node and co */
                    final String firstName  = ((Variable) first).getName();
                    final String secondName = ((Variable) second).getName();
                    if (!firstName.isEmpty() && !secondName.isEmpty()) {
                        result = firstName.equals(secondName);
                    } else {
                        result = PsiEquivalenceUtil.areElementsEquivalent(first, second);
                    }
                } else {
                    result = PsiEquivalenceUtil.areElementsEquivalent(first, second) ||
                             first.getText().equals(second.getText());
                }
            }
        } catch (final Throwable error) {
            if (error instanceof ProcessCanceledException) {
                throw error;
            }
            result = false;
        }
        return result;
    }
}
