import java.util.Date;

/**
 * Class for a note.
 */

public class Note {
    private String heading;
    private String information;
    private Date date;

    /**
     * Constructor.
     *
     * @param heading The heading of the note
     * @param information The main part of the note
     */
    public Note(String heading, String information) {
        this.heading = heading;
        this.information = information;
        this.date = new Date();
    }

    /**
     * Method that returns the heading of the note.
     *
     * @return The heading of the note
     */
    public String getHeading() {
        return this.heading;
    }

    /**
     * Method that returns the main part of the note.
     *
     * @return The main part of the note
     */
    public String getInformation() {
        return this.information;
    }

    /**
     * Method that returns the time when the note was added.
     *
     * @return The time when the note was added
     */
    public Date getDate() {
        return this.date;
    }
}
