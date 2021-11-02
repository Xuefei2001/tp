package seedu.duke.nusmods;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.GetTaskFailedException;
import seedu.duke.task.type.Lesson;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NusModsParserTest {
    NusModsParser parser = new NusModsParser();

    @Test
    void getModuleEvents_CS2113T_success() throws GetTaskFailedException {
        Lesson[] moduleLessons = parser.getLessons("CS2113T", "C02");

        for (Lesson moduleLesson : moduleLessons) {
            System.out.println(moduleLesson.getTaskEntryDescription());
        }

        assertEquals(4, moduleLessons.length);
    }

    @Test
    void getModuleEvents_noNetworkAndNoLocalCache_failure() {
        try {
            try {
                FileUtils.deleteDirectory(new File(NusModsParser.CACHEDIR)); // remove local cache if existing
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.setProperty("https.proxyHost", "localhost"); // simulate network down
            assertThrows(GetTaskFailedException.class,
                () -> parser.getLessons("CS2113T", "C02"));
        } finally {
            System.clearProperty("https.proxyHost"); // revive network regardlessly
        }
    }
}
