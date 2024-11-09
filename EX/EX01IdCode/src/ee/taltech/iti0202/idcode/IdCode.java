package ee.taltech.iti0202.idcode;

import java.util.HashMap;

public class IdCode {
    public static final int[] BIRTH_PLACE_RANGES = {0, 10, 20, 220, 270, 370, 420, 470, 490, 520, 570, 600,
            650, 710, 999};
    public static final int MAXIMUM_YEAR = 99;
    public static final int MAX_MONTH = 12;
    public static final int VALUE_31 = 31;
    public static final int VALUE_28 = 28;
    public static final int VALUE_30 = 30;
    public static final int KEY_NOVEMBER = 11;
    public static final int KEY_DECEMBER = 12;
    public static final int MODULA_ELEVEN = 11;
    public static final int FULLYEAR_FOURHUNDRED = 400;
    public static final int END_INDEX = 11;
    public static final int MAX_LENGTH = 11;
    private final String idCodeValue;

    enum Gender {
        MALE, FEMALE
    }

    /**
     * Method returns the id code.
     *
     * @return id code.
     */
    public String getIdCodeValue() {
        return idCodeValue;
    }

    public IdCode(String idCodeValue) {
        this.idCodeValue = idCodeValue;
        if (!isCorrect()) {
            throw new IllegalArgumentException("Not a correct ID code!");
        }
    }

    /**
     * Check if the id code is valid or not.
     *
     * @return boolean describing whether the id code was correct.
     */
    public boolean isCorrect() {
        if (idCodeValue.length() != MAX_LENGTH) {
            return false;
        }
        try {
            for (int i = 0; i < idCodeValue.length(); i++) {
                Integer.parseInt(String.valueOf(i));
            }
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return (isGenderNumberCorrect() && isDayNumberCorrect() && isMonthNumberCorrect() && isYearNumberCorrect()
                && isControlNumberCorrect());

    }

    /**
     * Get all information about id code.
     *
     * @return String containing information.
     */
    public String getInformation() {
        String birthMonthString = idCodeValue.substring(3, 5);
        String birthDayString = idCodeValue.substring(5, 7);
        return "This is a " + getGender() + " born on " + birthDayString + "." + birthMonthString + "."
                + getFullYear() + " in " + getBirthPlace();
    }

    /**
     * Get gender enum.
     *
     * @return enum describing person's gender
     */
    public Gender getGender() {
        if (this.idCodeValue.startsWith("3") || this.idCodeValue.startsWith("1")
                || this.idCodeValue.startsWith("5")) {
            return Gender.MALE;
        }
        if (this.idCodeValue.startsWith("2") || this.idCodeValue.startsWith("4")
                || this.idCodeValue.startsWith("6")) {
            return Gender.FEMALE;
        }
        return null;
    }

    /**
     * Get person's birth location.
     *
     * @return String with the person's birthplace.
     */
    public String getBirthPlace() {
        int[] birthPlaceRanges = BIRTH_PLACE_RANGES;
        String[] birthPlaceTowns = new String[]{"unknown", "Kuressaare", "Tartu", "Tallinn", "Kohtla-Järve",
                "Tartu", "Narva", "Pärnu", "Tallinn", "Paide", "Rakvere", "Valga", "Viljandi", "Võru", "unknown"};
        String birthPlaceString = idCodeValue.substring(7, 10);
        int birthPlaceInt = Integer.parseInt(birthPlaceString);
        for (int i = 0; i < birthPlaceRanges.length; i++) {
            if (birthPlaceInt <= birthPlaceRanges[i]) {
                return birthPlaceTowns[i];
            }
        }
        return null;
    }

    /**
     * Get the year that the person was born in.
     *
     * @return int with person's birth year.
     */
    public int getFullYear() {
        String returnString = "";
        String genderString = idCodeValue.substring(0, 1);
        String yearString = idCodeValue.substring(1, 3);

        int gender = Integer.parseInt(genderString);
        if (gender == 1 || gender == 2) {
            returnString = "18" + yearString;
            return Integer.parseInt(returnString);
        }
        if (gender == 3 || gender == 4) {
            returnString = "19" + yearString;
            return Integer.parseInt(returnString);
        } else if (gender == 5 || gender == 6) {
            returnString = "20" + yearString;
            return Integer.parseInt(returnString);
        }
        return Integer.parseInt(returnString);
    }


    /**
     * Check if gender number is correct.
     *
     * @return boolean describing whether the gender number is correct.
     */
    private boolean isGenderNumberCorrect() {
        String genderString = idCodeValue.substring(0, 1);
        try {
            int nr = Integer.parseInt(genderString);
            return nr > 0 && nr < 7;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if the year number is correct.
     *
     * @return boolean describing whether the year number is correct.
     */
    private boolean isYearNumberCorrect() {
        String yearNumberString = idCodeValue.substring(1, 3);
        int year = Integer.parseInt(yearNumberString);
        return year >= 0 & year <= MAXIMUM_YEAR;

    }

    /**
     * Check if the month number is correct.
     *
     * @return boolean describing whether the month number is correct.
     */
    private boolean isMonthNumberCorrect() {
        String monthNumber = idCodeValue.substring(3, 5);
        int month = Integer.parseInt(monthNumber);
        return 1 <= month && month <= MAX_MONTH;
    }

    /**
     * Check if the day number is correct.
     *
     * @return boolean describing whether the day number is correct.
     */
    private boolean isDayNumberCorrect() {
        HashMap<Integer, Integer> days = new HashMap<>();
        days.put(1, VALUE_31);
        days.put(2, VALUE_28);
        days.put(3, VALUE_31);
        days.put(4, VALUE_30);
        days.put(5, VALUE_31);
        days.put(6, VALUE_30);
        days.put(7, VALUE_31);
        days.put(8, VALUE_31);
        days.put(9, VALUE_30);
        days.put(10, VALUE_31);
        days.put(KEY_NOVEMBER, VALUE_30);
        days.put(KEY_DECEMBER, VALUE_31);

        if (isLeapYear(getFullYear())) {
            int oldDays = days.get(2);
            int newDays = oldDays + 1;
            days.replace(2, newDays);
        }


        String checkMonthNumber = idCodeValue.substring(3, 4);
        int month = Integer.parseInt(checkMonthNumber);
        if (month == 0) {
            String singleMonthNumber = idCodeValue.substring(4, 5);
            int monthNr = Integer.parseInt(singleMonthNumber);
            String dayNumber = idCodeValue.substring(5, 7);
            int dayNr = Integer.parseInt(dayNumber);
            int compare = days.get(monthNr);
            return dayNr <= compare;

        } else {
            String dayNr = idCodeValue.substring(5, 7);
            int dayNumber = Integer.parseInt(dayNr);
            String doubleMonthNumber = idCodeValue.substring(3, 5);
            int doubleMonth = Integer.parseInt(doubleMonthNumber);
            int compare2 = days.get(doubleMonth);
            return dayNumber <= compare2;
        }
    }

    /**
     * Check if the control number is correct.
     *
     * @return boolean describing whether the control number is correct.
     */
    private boolean isControlNumberCorrect() {
        int totalSum = 0;
        int checkSum;
        int[] weights1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        for (int i = 0; i < idCodeValue.length() - 1; i++) {
            char currentChar = idCodeValue.charAt(i);
            int nr = Integer.parseInt(String.valueOf(currentChar));
            totalSum += weights1[i] * nr;
        }
        checkSum = totalSum % MODULA_ELEVEN;

        if (checkSum == 10) {
            int[] weights2 = new int[]{3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
            totalSum = 0;
            for (int i = 0; i < idCodeValue.length() - 1; i++) {
                char currentChar = idCodeValue.charAt(i);
                int nr = Integer.parseInt(String.valueOf(currentChar));
                totalSum += weights2[i] * nr;
            }
            checkSum = totalSum % MODULA_ELEVEN;
            if (checkSum == 10) {
                checkSum = 0;
            }
            String lastValue = idCodeValue.substring(10, END_INDEX);
            int check = Integer.parseInt(lastValue);
            return check == checkSum;
        } else {
            String lastValue = idCodeValue.substring(10, END_INDEX);
            int check = Integer.parseInt(lastValue);
            return check == checkSum;
        }
    }

    /**
     * Check if the given year is a leap year.
     *
     * @param fullYear for digit for a year.
     * @return boolean describing whether the given year is a leap year.
     */
    private boolean isLeapYear(int fullYear) {
        return ((fullYear % 4 == 0) && (fullYear % 100 != 0)) || (fullYear % FULLYEAR_FOURHUNDRED == 0);
    }

    /**
     * Run tests.
     *
     * @param args info.
     */
    public static void main(String[] args) {
        IdCode validMaleIdCode = new IdCode("37605030299");
        //IdCode validMaleIdCode = new IdCode("2734983274327432");
        System.out.println(validMaleIdCode.isCorrect());
        System.out.println(validMaleIdCode.getInformation());
        System.out.println(validMaleIdCode.getGender());
        System.out.println(validMaleIdCode.getBirthPlace());
        System.out.println(validMaleIdCode.getFullYear());
        System.out.println(validMaleIdCode.isGenderNumberCorrect());
        System.out.println(validMaleIdCode.isYearNumberCorrect());
        System.out.println(validMaleIdCode.isMonthNumberCorrect());
        System.out.println(validMaleIdCode.isDayNumberCorrect());
        System.out.println(validMaleIdCode.isControlNumberCorrect());
        System.out.println(validMaleIdCode.isLeapYear(validMaleIdCode.getFullYear()));
    }
}
