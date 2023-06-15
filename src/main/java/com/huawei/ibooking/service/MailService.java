package com.huawei.ibooking.service;

import antlr.StringUtils;
import ch.qos.logback.classic.Logger;
import com.huawei.ibooking.business.BookingBusiness;
import com.huawei.ibooking.business.SeatBusiness;
import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.model.BookingDO;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudentDO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class MailService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(MailService.class);

    private boolean remindersent = false;
    @Autowired
    private BookingBusiness bookingBusiness;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private StudentBusiness studentBusiness;

    @Autowired
    private SeatBusiness seatBusiness;

    //将yml中spring.mail.username的内容注入到sendMailer
    @Value("${spring.mail.username}")
    private String sendMailer;

    private void checkMail(String to,String subject,String text){
        if(to.isEmpty()){
            throw new RuntimeException("收件人不能为空");
        }
        if(subject.isEmpty()){
            throw new RuntimeException("主题不能为空");
        }
        if(text.isEmpty()){
            throw new RuntimeException("内容不能为空");
        }
    }

    public void sendTextMailMessage(String to,String subject,String text){

        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            System.out.println("发送邮件成功："+sendMailer+"->"+to);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败："+e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 600000) //每隔十分钟检查一次
    public void sendReminder(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusMinutes(15); //提前15分钟提醒未签到
        List<BookingDO> bookingDOList = bookingBusiness.not_check_in();
        if(bookingDOList.isEmpty()){
            System.out.println("空");
            return ;
        }
        else {
            for (BookingDO bookingDO : bookingDOList) {
                LocalDateTime time1 = bookingDO.getStartTime(); //预约开始时间
                LocalDateTime default_reminder = time1.plusMinutes(10); //十分钟内迟到提醒
                LocalDateTime default_time = time1.plusMinutes(15); //十五分钟内未签到自动设置违约
                if (time1.isAfter(now) && time1.isBefore(reminderTime)) {
                    String to = studentBusiness.getEmail(bookingDO.getStuNum());
                    String subject = "签到提醒";
                    String text = "请及时签到";
                    sendTextMailMessage(to, subject, text);
                } else if (now.isAfter(time1) && now.isBefore(default_reminder)) { //超时提醒
                    String to = studentBusiness.getEmail(bookingDO.getStuNum());
                    String subject = "即将违约提醒";
                    String text = "即将违约";
                    sendTextMailMessage(to, subject, text);
                } else if (now.isAfter(default_time)) {
                    bookingBusiness.sign_in_success(bookingDO.getStuNum(), 2);
                    System.out.println("自动：超过签到时间违约");
                    List<SeatDO> seatDOS = seatBusiness.getSeat(bookingDO.getSeatId());
                    for (SeatDO seatDO : seatDOS) {
                        seatBusiness.saveSeat(seatDO); //释放座位，即设置座位可用
                    }
                    if (bookingDO.getStatus() != 2) {
                        String to = studentBusiness.getEmail(bookingDO.getStuNum());
                        String subject = "自动签到超时提醒";
                        String text = "您已违约";
                        sendTextMailMessage(to, subject, text);
                    }
                }
            }
        }
            List<BookingDO> bookingDOList2 = bookingBusiness.sign_out(); //未签退列表
            for (BookingDO bookingDO : bookingDOList2) {
                LocalDateTime endtime = bookingDO.getEndTime();
                LocalDateTime default_time = endtime.plusMinutes(10);
                System.out.println("违约时间" + default_time);
                if (now.isAfter(default_time)) {
                    bookingBusiness.sign_in_success(bookingDO.getStuNum(), 2);
                    System.out.println("自动：超过签退违约");
                    List<SeatDO> seatDOS = seatBusiness.getSeat(bookingDO.getSeatId());
                    for (SeatDO seatDO : seatDOS) {
                        seatBusiness.saveSeat(seatDO); //释放座位，即设置座位可用
                    }
                    if (bookingDO.getStatus() != 2) {
                        String to = studentBusiness.getEmail(bookingDO.getStuNum());
                        String subject = "自动签退超时违约提醒";
                        String text = "您已违约";
                        sendTextMailMessage(to, subject, text);
                        //bookingDOList2.remove(bookingDO);
                    }
                }
                else{
                    System.out.println("未超时");
                }
            }
    }

}
