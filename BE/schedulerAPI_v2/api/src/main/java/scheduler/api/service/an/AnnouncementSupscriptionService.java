package scheduler.api.service.an;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scheduler.api.domain.an.AnnouncementSchedule;
import scheduler.api.domain.an.AnnouncementSubscription;
import scheduler.api.dto.an.ReadingAsubDto;
import scheduler.api.repository.an.AnnouncementSubscriptionRepository;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AnnouncementSupscriptionService {

    @Autowired
    private AnnouncementSubscriptionRepository announcementSubscriptionRepository;
    @Autowired
    private AnnouncementScheduleService announcementScheduleService;

    @Transactional
    public void subscribe(Long memberId, AnnouncementSchedule announcementSchedule) {

        //구독하고자 하는 공고
        AnnouncementSchedule getAnnouncementSchedule = announcementScheduleService.createOrGet(announcementSchedule);


        if (announcementSubscriptionRepository.isMapped(memberId, getAnnouncementSchedule.getId()) == null) {

            AnnouncementSubscription newAnnouncementSubscription = new AnnouncementSubscription(getAnnouncementSchedule, memberId);
            announcementSubscriptionRepository.save(newAnnouncementSubscription);

        } else {
            //이미 구독한 공고 입니다.에러 응답
        }

    }

    @Transactional
    public void cancelSubscribe(List<Long> asubIds) {
        announcementSubscriptionRepository.deleteThem(asubIds);
    }


    @Transactional
    public List<ReadingAsubDto> findAll(Long memberId) {
        return announcementSubscriptionRepository.findAll(memberId);
    }


}
