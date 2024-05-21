package ru.nsu.meshcheryakova.handling;

import com.aspose.html.HTMLDocument;
import com.aspose.html.dom.Element;
import com.aspose.html.dom.Text;
import java.util.HashMap;
import ru.nsu.meshcheryakova.dsl.Course;
import ru.nsu.meshcheryakova.dsl.Group;
import ru.nsu.meshcheryakova.dsl.Student;
import ru.nsu.meshcheryakova.dsl.Task;

/**
 * The class to create HTML report.
 */
public class HTMLResults {
    private static HTMLDocument document = new HTMLDocument();
    private static HashMap<Task, HashMap<Student, TaskInfo>> tasksInfo;
    private static HashMap<Group, HashMap<Student, StudentInfo>> groupsInfo;

    /**
     * Main function for creating tables.
     *
     * @param groupsInformation statistics by groups.
     * @param tasksInformation statistics by tasks.
     * @param course course information.
     */
    public static void makeReport(HashMap<Group, HashMap<Student, StudentInfo>> groupsInformation,
                                  HashMap<Task, HashMap<Student, TaskInfo>> tasksInformation,
                                  Course course) {
        tasksInfo = tasksInformation;
        groupsInfo = groupsInformation;

        Element style = document.createElement("style");
        style.setTextContent("table, th, td { border: 1px solid #000000; "
                + "border-collapse: collapse;}");
        Element head = document.getElementsByTagName("head").get_Item(0);
        head.appendChild(style);

        Element body = document.getBody();

        for (Task task : course.getTasks()) {
            if (tasksInfo.containsKey(task)) {
                createTableTaskInfo(task, body);
                createSpace(body);
            }
        }

        for (Group group : groupsInfo.keySet()) {
            createTableGroupInfo(group, body, course);
            createSpace(body);
        }

        String savePath = "src/main/resources/report.html";
        document.save(savePath);
    }

    /**
     * Create table with task statistic.
     *
     * @param task task.
     * @param body body of the HTML-doc.
     */
    private static void createTableTaskInfo(Task task, Element body) {
        Element table = document.createElement("table");

        Element tbody = document.createElement("tbody");
        table.appendChild(tbody);

        Element tr = createTableRow(tbody);
        Element th = createTableHeader(tr, String.format("%s (%s)", task.getId(), task.getName()));
        th.setAttribute("colspan", "6");

        tr = createTableRow(tbody);
        createTableHeader(tr, "Student");
        createTableHeader(tr, "Build");
        createTableHeader(tr, "Documentation");
        createTableHeader(tr, "Style");
        createTableHeader(tr, "Tests (All/Passed/Failed/Not completed)");
        createTableHeader(tr, "Points");

        HashMap<Student, TaskInfo> students = tasksInfo.get(task);

        for (Student student : students.keySet()) {
            tr = createTableRow(tbody);
            createTableData(tr, student.getName());

            TaskInfo taskInfo = students.get(student);

            if (taskInfo.isBuild()) {
                createTableData(tr, "+");
            } else {
                createTableData(tr, "-");
            }

            if (taskInfo.isDocumentation()) {
                createTableData(tr, "+");
            } else {
                createTableData(tr, "-");
            }

            if (taskInfo.isCheckStyle()) {
                createTableData(tr, "+");
            } else {
                createTableData(tr, "-");
            }

            if (taskInfo.isRunningTests()) {
                createTableData(tr, String.format("%d/%d/%d/%d", taskInfo.getTests().getAll(),
                        taskInfo.getTests().getPassed(), taskInfo.getTests().getFailed(),
                        taskInfo.getTests().getNotCompleted()));
            } else {
                createTableData(tr, "-");
            }

            createTableData(tr, taskInfo.getPoints().toString());
        }
        body.appendChild(table);
    }

    /**
     * Create table with group statistic.
     *
     * @param group group.
     * @param body body of the HTML-doc.
     * @param course course information.
     */
    public static void createTableGroupInfo(Group group, Element body, Course course) {
        Element table = document.createElement("table");

        Element tbody = document.createElement("tbody");
        table.appendChild(tbody);

        Element tr = createTableRow(tbody);
        Element th = createTableHeader(tr, String.format("General statistics of the group %s",
                group.getNumber()));
        th.setAttribute("colspan", String.valueOf(course.getTasks().size() + 3));

        tr = createTableRow(tbody);
        createTableHeader(tr, "Student");

        for (Task task : course.getTasks()) {
            createTableHeader(tr, task.getId());
        }

        createTableHeader(tr, "Amount of points");
        createTableHeader(tr, "Final grade");

        HashMap<Student, StudentInfo> students = groupsInfo.get(group);

        for (Student student : students.keySet()) {
            tr = createTableRow(tbody);
            createTableData(tr, student.getName());

            StudentInfo studentInfo = students.get(student);
            HashMap<Task, Integer> tasks = studentInfo.getTasks();
            for (Task task : course.getTasks()) {
                if (tasks.containsKey(task)) {
                    createTableData(tr, tasks.get(task).toString());
                } else {
                    createTableData(tr, "-");
                }
            }

            createTableData(tr, studentInfo.getSumOfPoints().toString());
            createTableData(tr, studentInfo.getFinalGrade().toString());
        }
        body.appendChild(table);
    }

    /**
     * Method for creation table row.
     *
     * @param tbody the body of the table.
     * @return the row element.
     */
    private static Element createTableRow(Element tbody) {
        Element tr = document.createElement("tr");
        tbody.appendChild(tr);
        return tr;
    }

    /**
     * Method for creation table header.
     *
     * @param tr the table row.
     * @param text header text.
     * @return table header element.
     */
    private static Element createTableHeader(Element tr, String text) {
        Element th = document.createElement("th");
        Text title = document.createTextNode(text);
        th.appendChild(title);
        tr.appendChild(th);
        return th;
    }

    /**
     * Method for creation table data.
     *
     * @param tr the table row.
     * @param text data text.
     * @return table data element.
     */
    private static Element createTableData(Element tr, String text) {
        Element td = document.createElement("td");
        Text data = document.createTextNode(text);
        td.appendChild(data);
        tr.appendChild(td);
        return td;
    }

    /**
     * Method for creation space between tables.
     *
     * @param body body of the HTML-doc.
     */
    private static void createSpace(Element body) {
        Element spacer = document.createElement("div");
        spacer.setAttribute("style", "margin-bottom: 20px;");
        body.appendChild(spacer);
    }
}
