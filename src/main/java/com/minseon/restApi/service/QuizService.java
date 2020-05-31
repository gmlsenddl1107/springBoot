package com.minseon.restApi.service;


import com.minseon.restApi.entity.Quiz;
import com.minseon.restApi.repository.QuizRepository;
import com.sun.media.sound.InvalidDataException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

//    @Transactional
//    public void generateQuiz() {
//
//        List<Quiz> quizzes = new ArrayList<Quiz>();
//        for (int i = 1; i < 10; i++) {
//            quizzes.add(new Quiz("quiz".concat(Integer.toString(i)), 36, "n", "폭죽", "물", "페트병 음료수", "풍선", 4, "", "", 12));
//        }
//
//        quizRepository.saveAll(quizzes);
//    }

    @Transactional
    public int saveQuizzes(List<Quiz> quizzes) {
        quizRepository.saveAll(quizzes);
        return quizzes.size();
    }

    public List<Quiz> uploadExcelQuiz(MultipartFile file, int isColumnName) throws IOException {

        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        int rowSize = sheet.getLastRowNum();

        if (rowSize < 2) {
            throw new InvalidDataException("the number of row in file must be larger than 1. (included column name row) ");
        }

        List<Quiz> quizzes = new ArrayList<>();

        for (int rowNum = isColumnName; rowNum < rowSize; rowNum++) {
            Row row = sheet.getRow(rowNum);

            try {
                Quiz quiz = new Quiz(row);
                quizzes.add(quiz);
            } catch (Exception e) {
                throw new InvalidDataException(rowNum + " line data is invalid: " );
                /////////////
                ////////////////
                /////////////row 에러 처리
            }
        }

        return quizzes;
    }


    //read only와 같은 권한 제어어
    @Transactional()
    public Slice<Quiz> getQuizzesPage(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Slice<Quiz> quizzes = quizRepository.findAllBy(pageable);

        return quizzes;
    }
}
