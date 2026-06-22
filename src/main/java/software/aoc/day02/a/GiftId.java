package software.aoc.day02.a;

public record GiftId(long value) {

    public boolean isInvalid() {
        return isInvalidString(String.valueOf(value));
    }

    private boolean isInvalidString(String idStr) {
        return hasEvenLength(idStr) && halvesAreEqual(idStr);
    }

    private boolean hasEvenLength(String s) {
        return s.length() % 2 == 0;
    }

    private boolean halvesAreEqual(String s) {
        return firstHalf(s).equals(secondHalf(s));
    }

    private String firstHalf(String s) {
        return s.substring(0, midpoint(s));
    }

    private String secondHalf(String s) {
        return s.substring(midpoint(s));
    }

    private int midpoint(String s) {
        return s.length() / 2;
    }
}
