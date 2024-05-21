import java.time.LocalDate

tasks = [
        {
            id = "Task_1_1_1"
            name = "Heapsort"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 9, 29)
            hardDeadLine = LocalDate.of(2023, 10, 6)
        },
        {
            id = "Task_1_1_2"
            name = "Polynom"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 10, 6)
            hardDeadLine = LocalDate.of(2023, 10, 13)
        },
        {
            id = "Task_1_2_1"
            name = "Tree"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 10, 13)
            hardDeadLine = LocalDate.of(2023, 10, 20)
        },
        {
            id = "Task_1_2_2"
            name = "Graph"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 10, 27)
            hardDeadLine = LocalDate.of(2023, 11, 3)
        },
        {
            id = "Task_1_3_1"
            name = "Substring"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 11, 10)
            hardDeadLine = LocalDate.of(2023, 11, 17)
        },
        {
            id = "Task_1_4_1"
            name = "Record book"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 11, 24)
            hardDeadLine = LocalDate.of(2023, 12, 1)
        },
        {
            id = "Task_1_5_1"
            name = "Calculator"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 12, 8)
            hardDeadLine = LocalDate.of(2023, 12, 15)
        },
        {
            id = "Task_1_5_2"
            name = "Notebook"
            maxScore = 1
            softDeadLine = LocalDate.of(2023, 12, 22)
            hardDeadLine = LocalDate.of(2023, 12, 29)
        }
]

groups = [
        {
            number = "22214"
            students = [
                    {
                        username = "an-mrv"
                        name = "Anastasia Meshcheryakova"
                        repository = "https://github.com/an-mrv/OOP"
                        assignments = [
                                tasks[1],
                                tasks[2],
                                tasks[4],
                                tasks[5],
                                tasks[6]
                        ]
                    },
                    {
                        username = "mixdone"
                        name = "Michail Diza"
                        repository = "https://github.com/mixdone/OOP"
                        assignments = [
                                tasks[1],
                                tasks[5]
                        ]
                    }
            ]
        },
        {
            number = "22213"
            students = [
                    {
                        username = "daria-barsukova"
                        name = "Daria Barsukova"
                        repository = "https://github.com/daria-barsukova/OOP"
                        assignments = [
                                tasks[0],
                                tasks[1],
                                tasks[2],
                                tasks[3]
                        ]
                    }
            ]
        }
]

gradeSettings {
    minimumPointsForFive = 8
    minimumPointsForFour = 5
    minimumPointsForThree = 3
}