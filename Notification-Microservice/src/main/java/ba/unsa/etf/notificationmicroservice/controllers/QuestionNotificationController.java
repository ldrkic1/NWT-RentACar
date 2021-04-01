package ba.unsa.etf.notificationmicroservice.controllers;

import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.services.QuestionNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/questionNotifications")
public class QuestionNotificationController {
    @Autowired
    private QuestionNotificationService service;

    @PostMapping("/newQuestionNotification")
    public QuestionNotification addQuestionNotification(@RequestBody @Valid @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) QuestionNotification questionNotification, Errors errors){
        if (errors.hasErrors()) {
            String errorsMessage = "";
            for (int i=0; i<errors.getAllErrors().size(); i++)
            {
                if(errors.getAllErrors().get(i).toString().contains("title"))
                {
                    errorsMessage += "Blank or empty title. ";
                }

                if(errors.getAllErrors().get(i).toString().contains("content"))
                {
                    errorsMessage += "Blank or empty content. ";
                }

            }
            throw new ValidationException(errorsMessage);
        }
        return service.addQuestionNotification(questionNotification);
    }
    @GetMapping(path = "/all")
    public List<QuestionNotification> getQuestionNotifications() {
        return service.getAllQuestionNotifications();
    }

    @GetMapping(path = "/client")
    public List<QuestionNotification> getClientQuestionNotifications(@RequestParam(value = "id") Long id) {
        return service.getAllClientQuestionNotifications(id); }

    @GetMapping(path = "/question")
    public QuestionNotification getQuestionNotificationsByQuestion(@RequestParam(value = "id") Long id) {
        return service.getQuestionNotificationByQuestion(id); }

    @GetMapping(path = "/questionNotification")
    public QuestionNotification getQuestionNotificationById(@RequestParam(value = "id") Long id) {
        return service.getQuestionNotificationById(id); }

    @GetMapping(path = "/between")
    public List<QuestionNotification> getNotificationsBetweenTwoDates(@RequestParam(value = "localDateTime1") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime1, @RequestParam(value = "localDateTime2") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime2) {
        return service.getAllQuestionNotificationsBetweenTwoDates(localDateTime1, localDateTime2); }

}
