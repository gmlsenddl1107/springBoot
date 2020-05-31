package com.minseon.restApi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "paige_quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz{

//    @NotNull
//    @Id
//    @GeneratedValue
//    private Long id;

    //중복 금지
    @NotNull
    @Id
    @Size(min=1)
    @Column(length = 50)
    private String question;

    @Column()
    private Integer quizCategory;

    @Column(length=20)
    private String duration;

    @NotNull
    @NotEmpty
    @Column(length=20)
    private String answer;

    @Column(length=20)
    private String option1;

    @Column(length=20)
    private String option2;

    @Column(length=20)
    private String option3;

    @Column
    private Integer numOptions;

    @Column(length = 20)
    private String comment;

    @Column(length = 20)
    private String qaTeamId;

    @Column
    private Integer qaPlayerId;

    public Quiz(Row row) {

        question = cellValueToString(row.getCell(0));
        quizCategory = Integer.parseInt(cellValueToString(row.getCell(1)));
        duration = cellValueToString(row.getCell(2));
        answer = cellValueToString(row.getCell(3));
        option1 = cellValueToString(row.getCell(4));
        option2 = cellValueToString(row.getCell(5));
        option3 = cellValueToString(row.getCell(6));
        numOptions = Integer.parseInt(cellValueToString(row.getCell(7)));
        comment = cellValueToString(row.getCell(8));
        qaTeamId = cellValueToString(row.getCell(9));
        qaPlayerId = Integer.parseInt(cellValueToString(row.getCell(10)));
    }

    private String cellValueToString(Cell cell) {

        String value = "";

        switch (cell.getCellTypeEnum()) {
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            case NUMERIC:
                value = String.valueOf((int) cell.getNumericCellValue());
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
        }

        return value;
    }
}