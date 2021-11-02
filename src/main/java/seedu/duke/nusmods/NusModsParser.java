package seedu.duke.nusmods;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import seedu.duke.exception.GetTaskFailedException;
import seedu.duke.task.type.Lesson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.StreamSupport;

import static org.apache.commons.io.FileUtils.copyURLToFile;

public class NusModsParser {
    public static final String CACHEDIR = "cache/";
    private static final String ENDPOINT = "https://api.nusmods.com/v2/";
    private static final int TIMEOUT = 5000;

    /**
     * Get Lesson objects of a particular class.
     * @param moduleCode The module code to be queried
     * @param classNo The class number to be queried
     * @return An array of Lessons denoting all class occurrences
     * @throws GetTaskFailedException If there is neither network connection nor local cache
     */
    public Lesson[] getLessons(String moduleCode, String classNo) throws GetTaskFailedException {
        Reader moduleReader;
        try {
            moduleReader = getModuleReader(moduleCode);
        } catch (IOException ioe) {
            throw new GetTaskFailedException(ioe.getMessage());
        }
        JsonObject obj = JsonParser.parseReader(moduleReader).getAsJsonObject();
        JsonArray semesterData = obj.getAsJsonArray("semesterData");
        Lesson[] lessons = null;
        for (JsonElement element : semesterData) {
            JsonObject semesterObject = element.getAsJsonObject();
            if (Semester.fromInt(semesterObject.get("semester").getAsInt()) != Semester.getSemester()) {
                continue;
            }
            JsonArray timetable = semesterObject.get("timetable").getAsJsonArray();
            lessons = StreamSupport.stream(timetable.spliterator(), true)
                .map(JsonElement::getAsJsonObject)
                .filter(l -> classNo.equals(l.get("classNo").getAsString()))
                .map(l -> {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                    return new Lesson(moduleCode, classNo,
                        DayOfWeek.from(DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)
                                .parse(l.get("day").getAsString())),
                        LocalTime.parse(l.get("startTime").getAsString(), timeFormatter),
                        LocalTime.parse(l.get("endTime").getAsString(), timeFormatter),
                        Semester.acadWeeksToRealWeeks((new Gson()).fromJson(l.get("weeks"), int[].class)));
                })
                .toArray(Lesson[]::new);
        }
        return lessons;
    }

    private Reader getModuleReader(String moduleCode) throws IOException {
        if (!(new File(getModuleFilename(moduleCode)).isFile())) {
            // file not existing
            getModuleJson(moduleCode);
        }
        return new FileReader(getModuleFilename(moduleCode));
    }

    private String getModuleFilename(String moduleCode) {
        return CACHEDIR + moduleCode + ".json";
    }

    private static String getModuleUrl(String moduleCode) {
        return ENDPOINT + formatAcadYear(getAcadYear()) + "/modules/" + moduleCode + ".json";
    }

    private void getModuleJson(String moduleCode) throws IOException {
        copyURLToFile(new URL(getModuleUrl(moduleCode)), new File(getModuleFilename(moduleCode)), TIMEOUT, TIMEOUT);
    }

    private static Year getAcadYear() {
        YearMonth current = YearMonth.now();
        Year year = Year.of(current.getYear());
        if (current.getMonth().compareTo(Month.AUGUST) < 0) {
            year = year.minusYears(1);
        }
        return year;
    }

    private static String formatAcadYear(Year year) {
        return String.format("%tY-%tY", year, year.plusYears(1));
    }
}
