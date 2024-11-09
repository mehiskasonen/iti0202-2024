package projbasetest;

import ee.taltech.iti0202.sportsclub.SportsClubManager;
import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.training.GroupTrainingSession;
import ee.taltech.iti0202.sportsclub.training.PersonalTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;
import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.*;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyBaseTest {

    protected List<Member> testMembers;
    protected List<SportsClub> testClubs;
    protected List<TrainingSession> testSessions;

    /**
     * Manager      Club1       Training1 (Judo)        Session1, Session2
     *              Member1                             Member1   Member3
     *              Member2                             Member2
     *              Member3
     *                          Training2 (Running)     Session3
     *                                                  Member4
     *                          Training3 (HIIT)        Session4
     *                                                  Member5
     *                          Training4 (Basketball)  Session5
     *                                                  Member4
     *              Club2       Training5 (Tennis)      Session6
     *              Member6
     *              Club3       Training6 (Judo)        Session7, Session8
     *              Member1                             Member1   Member1    24BP
     *              Member2                             Member2              4BP
     *              Member3                             Member3              4BP
     *              Club4       Training7 (Judo)        Session9, Session10
     *              Member1                             Member1   Member3
     *              Member2                             Member2
     *              Member3
     */
    @BeforeEach
    void setUp() throws RegistrationException, TransactionException {
        SportsClubManager manager = new SportsClubManager();

        testMembers = new ArrayList<>();
        Member member1 = new Member("Member1", true);
        member1.getMemberWallet().setAmount(BigDecimal.valueOf(1000));
        Member member2 = new Member("Member2", true);
        member2.getMemberWallet().setAmount(BigDecimal.valueOf(1000));
        Member member3 = new Member("Member3", true);
        member3.getMemberWallet().setAmount(BigDecimal.valueOf(1000));
        Member member4 = new Member("Member4", true);
        member4.getMemberWallet().setAmount(BigDecimal.valueOf(1000));
        Member member5 = new Member("Member5", true);
        member5.getMemberWallet().setAmount(BigDecimal.valueOf(1000));
        Member member6 = new Member("Member6", true);
        member5.getMemberWallet().setAmount(BigDecimal.valueOf(1000));
        Member member7 = new Member("Member6", true);
        member5.getMemberWallet().setAmount(BigDecimal.valueOf(1000));

        testMembers.add(member1);
        testMembers.add(member2);
        testMembers.add(member3);
        testMembers.add(member4);
        testMembers.add(member5);
        testMembers.add(member6);
        testMembers.add(member7);

        testClubs = new ArrayList<>();
        SportsClub club1 = new SportsClub("Club1");
        SportsClub club2 = new SportsClub("Club2");
        SportsClub club3 = new SportsClub("Club3");
        SportsClub club4 = new SportsClub("Club4");

        testClubs.add(club1);
        testClubs.add(club2);
        testClubs.add(club3);
        testClubs.add(club4);


        //Register
        Trainer trainer1 = new Trainer("Trainer1", Collections.singletonList(JUDO));
        Trainer trainer2 = new Trainer("Trainer2", Collections.singletonList(RUNNING));
        Trainer trainer3 = new Trainer("Trainer3", Collections.singletonList(HIIT));
        Trainer trainer4 = new Trainer("Trainer4", Collections.singletonList(BASKETBALL));
        Trainer trainer5 = new Trainer("Trainer5", Collections.singletonList(TENNIS));

        Training training1 = new Training("Training1", JUDO, trainer1);
        Training training2 = new Training("Training2", RUNNING, trainer2);
        club1.addTraining(training1);
        //club4.addTraining(training1);

        club1.addTraining(training2);
        Training training3 = new Training("Training3", HIIT, trainer1);
        club1.addTraining(training3);
        Training training4 = new Training("Training4", BASKETBALL, trainer1);
        club1.addTraining(training4);

        Training training6 = new Training("Training6", JUDO, trainer1);
        club3.addTraining(training6);

        Training training7 = new Training("Training7", JUDO, trainer1);
        club4.addTraining(training7);

        PersonalTrainingSession personalJudoSession8 = new PersonalTrainingSession("personalJudo",
                JUDO, Optional.of(trainer1), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        GroupTrainingSession groupJudoSession7 = new GroupTrainingSession("GroupJudo",
                JUDO, Optional.of(trainer1), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        testSessions = new ArrayList<>();
        GroupTrainingSession groupJudoSession1 = new GroupTrainingSession("GroupJudo",
                JUDO, Optional.of(trainer1), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        GroupTrainingSession groupJudoSession2 = new GroupTrainingSession("GroupJudo",
                JUDO, Optional.of(trainer1), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        GroupTrainingSession groupRunningSession3 = new GroupTrainingSession("GroupRunning",
                RUNNING, Optional.of(trainer2), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        GroupTrainingSession groupHIITSession4 = new GroupTrainingSession("GroupHIIT",
                HIIT, Optional.of(trainer3), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        GroupTrainingSession groupBasketballSession5 = new GroupTrainingSession("GroupBasketball",
                BASKETBALL, Optional.of(trainer4), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        testSessions.add(groupHIITSession4);
        testSessions.add(groupBasketballSession5);
        testSessions.add(groupRunningSession3);
        testSessions.add(groupJudoSession2);
        GroupTrainingSession groupTennisSession6 = new GroupTrainingSession("GroupTennis",
                JUDO, Optional.of(trainer5), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        GroupTrainingSession groupJudoSession9 = new GroupTrainingSession("GroupJudo",
                JUDO, Optional.of(trainer1), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        GroupTrainingSession groupJudoSession10 = new GroupTrainingSession("GroupJudo",
                JUDO, Optional.of(trainer1), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        //Add sessions to trainings.
        training1.addTrainingSession(groupJudoSession1);
        training1.addTrainingSession(groupJudoSession2);
        training2.addTrainingSession(groupRunningSession3);
        training3.addTrainingSession(groupHIITSession4);
        training4.addTrainingSession(groupBasketballSession5);
        training6.addTrainingSession(groupJudoSession7);
        training6.addTrainingSession(personalJudoSession8);
        training7.addTrainingSession(groupJudoSession9);
        training7.addTrainingSession(groupJudoSession10);


        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership1 = builder
                .withClub(club1)
                .build();

        FullMembership membership2 = builder
                .withClub(club3)
                .build();

        FullMembership membership3 = builder
                .withClub(club4)
                .build();


        //BuyMemberships
        member1.buyMembership(club1, membership1);
        member1.buyMembership(club3, membership2);
        member1.buyMembership(club4, membership3);

        member2.buyMembership(club1, membership1);
        member2.buyMembership(club3, membership2);
        member2.buyMembership(club4, membership3);

        member3.buyMembership(club1, membership1);
        member3.buyMembership(club3, membership2);
        member3.buyMembership(club4, membership3);


        member4.buyMembership(club1, membership1);
        member5.buyMembership(club1, membership1);
        member5.buyMembership(club2, membership1);


        //Register members to sessions.
        member1.registerToTrainingSession(groupJudoSession1);
        member1.registerToTrainingSession(groupJudoSession7);
        member1.registerToTrainingSession(personalJudoSession8);
        member1.registerToTrainingSession(groupJudoSession9);

        member2.registerToTrainingSession(groupJudoSession1);
        member2.registerToTrainingSession(groupJudoSession7);
        member2.registerToTrainingSession(groupJudoSession9);

        member3.registerToTrainingSession(groupJudoSession2);
        member3.registerToTrainingSession(groupJudoSession7);
        member3.registerToTrainingSession(groupJudoSession10);


        member4.registerToTrainingSession(groupRunningSession3);
        member5.registerToTrainingSession(groupHIITSession4);
        member4.registerToTrainingSession(groupBasketballSession5);
    }


}
