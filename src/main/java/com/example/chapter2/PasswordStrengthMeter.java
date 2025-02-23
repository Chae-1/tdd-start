package com.example.chapter2;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String password) {
        if (password == null || password.isEmpty()) return PasswordStrength.INVALID;
        int metCounts = getMetCriteriaCounts(password);

        if (metCounts <= 1) return PasswordStrength.WEAK;
        if (metCounts == 2) return PasswordStrength.NORMAL;

        return PasswordStrength.STRONG;
    }

    private int getMetCriteriaCounts(String password) {
        int metCounts = 0;
        if (password.length() >= 8) metCounts++;
        if (meetsContainingNumberCriteria(password)) metCounts++;
        if (meetsContainingUppercaseCriteria(password)) metCounts++;
        return metCounts;
    }

    private boolean meetsContainingUppercaseCriteria(String password) {
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingNumberCriteria(String password) {
        boolean numberContained = false;
        for (char ch : password.toCharArray()) {
            // 숫자를 포함하고 있는 경우.
            if (ch >= '0' && ch <= '9') {
                numberContained = true;
                break;
            }
        }
        return numberContained;
    }
}
