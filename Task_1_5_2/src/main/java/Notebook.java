import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for a notebook.
 */
public class Notebook {
    private ArrayList<Note> notes;

    /**
     * Constructor.
     */
    public Notebook() {
        this.notes = new ArrayList<>();
    }

    /**
     * Method for adding a new note.
     *
     * @param heading The heading of the note
     * @param information The main part of the note
     */
    public void addNote(String heading, String information) {
        Note note = new Note(heading, information);
        this.notes.add(note);
    }

    /**
     * Method for deleting all notes with the given heading.
     *
     * @param heading The given heading of the note
     */
    public void removeNote(String heading) {
        int count = this.notes.size();
        notes.removeIf(note -> note.getHeading().equals(heading));
        if (count == this.notes.size()) {
            throw new IllegalArgumentException("Such a note does not exist!");
        }
    }

    /**
     * Method that outputs all notes sorted by the time they were added in the format:
     * Heading: Information.
     */
    public void showAllNotes() {
        for (Note note : this.notes) {
            System.out.print(note.getHeading() + ": " + note.getInformation() + '\n');
        }
    }

    /**
     * A method that outputs all notes sorted by the time they were added from a given
     * time interval and containing keywords in the title in the format:
     * Heading: Information.
     *
     * @param start The beginning of the time interval
     * @param end The ending of the time interval
     * @param keywordsList A list of keywords that should be in the heading
     */
    public void showSomeNotes(String start, String end, ArrayList<String> keywordsList) throws
            ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy H:mm");
        Date startDate = df.parse(start);
        Date endDate = df.parse(end);
        for (Note value : this.notes) {
            int count = 0;
            Date noteDate = value.getDate();
            if ((noteDate.getTime() >= startDate.getTime())
                    && (noteDate.getTime() <= endDate.getTime())) {
                for (String keyword : keywordsList) {
                    if (value.getHeading().toLowerCase().contains(keyword.toLowerCase())) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == keywordsList.size()) {
                    System.out.print(value.getHeading() + ": " + value.getInformation() + '\n');
                }
            } else if (noteDate.getTime() > endDate.getTime()) {
                return;
            }
        }
    }
}
