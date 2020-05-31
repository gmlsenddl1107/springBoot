package com.minseon.restApi.controller;


import com.minseon.restApi.dto.RestApiResponse;
import com.minseon.restApi.entity.Quiz;
import com.minseon.restApi.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
//@Slf4j
public class QuizController {

    private final QuizService quizService;

//    /**
//     * get all quizzes in paige_quiz table
//     *
//     * @return
//     */
//    @GetMapping(path = "/quizzes")
//    public RestApiResponse<List<Quiz>> getQuizzes() {
//        //quizService.generateQuiz();
//        List<Quiz> quizzes = quizService.getQuizzes();
//
//        return new RestApiResponse<>(200, quizzes, "");
//    }

    /**
     * get quiz in requested page
     *
     * @param page     page number
     * @param pageSize page size
     * @return the list of quiz in request page
     */
    @GetMapping(path = "/quizzes/{page}")
    public RestApiResponse<List<Quiz>> getQuizzesPerPage(@Valid @PathVariable @Min(1) int page,
                                                         @Valid @RequestParam @Min(1) @NotNull Integer pageSize) {

//        log.info("some log");
        List<Quiz> quizzes = quizService.getQuizzesPage(page, pageSize).getContent();
        return new RestApiResponse<>(200, "", quizzes);
    }

    /**
     * @param file xlsx format file
     * @return the number of quizzes which is saved in DB successfully
     * @throws IOException
     * @throws InvalidFormatException
     */
    @PostMapping(path = "/quizzes")
    public RestApiResponse<Integer> saveQuizzesExcel(@Valid @RequestBody @NotNull MultipartFile file)
            throws IOException, InvalidFormatException, EmptyFileException {

        if (file.isEmpty()) {
            throw new EmptyFileException();
        }

        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            throw new InvalidFormatException("File must be 'xlsx' format. ");
        }

        List<Quiz> quizzes = quizService.uploadExcelQuiz(file, 1);
        int savedNum = quizService.saveQuizzes(quizzes);
        return new RestApiResponse<>(200, "", savedNum);
    }
}