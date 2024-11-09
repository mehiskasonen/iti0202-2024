package ee.taltech.iti0202.sportsclub.registration;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;
import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.BASKETBALL;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JOGA;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    @Test
    void testCanRegisterFailsMaxParticipantsReached() throws RegistrationException  {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        club.addTrainer(generalTrainer);

        Member member = new Member("Member", true);
        club.addMember(member);
        member.addClub(club);

        Training training1 = new Training("Judo", JUDO, generalTrainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(generalTrainer));
        judoSession.setMaxParticipants(0);

        club.addTraining(training1);
        training1.addTrainingSession(judoSession);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, judoSession).registerMemberToSession(member, judoSession);
        });
        assertEquals(RegistrationException.Reason.MAX_PARTICIPANTS_REACHED, exception.getReason());
    }

    @Test
    void testCanRegisterFailsMemberFeeNotPaid() throws TransactionException {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        club.addTrainer(generalTrainer);

        Member member = new Member("Member", false);
        club.addMember(member);
        member.addClub(club);

        Training training1 = new Training("Judo", JUDO, generalTrainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(generalTrainer));
        judoSession.setMaxParticipants(10);

        club.addTraining(training1);
        training1.addTrainingSession(judoSession);

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member.buyMembership(club, membership);
        membership.setExpiration(true);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, judoSession).registerMemberToSession(member, judoSession);
        });
        assertEquals(RegistrationException.Reason.MEMBER_FREE_NOT_PAID, exception.getReason());
    }

    @Test
    void testCanRegisterFailsMemberAlreadyRegistered() throws RegistrationException, TransactionException {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        club.addTrainer(generalTrainer);

        Member member = new Member("Member", true);
        club.addMember(member);
        member.addClub(club);

        Training training1 = new Training("Judo", JUDO, generalTrainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(generalTrainer));
        judoSession.setMaxParticipants(10);

        club.addTraining(training1);
        training1.addTrainingSession(judoSession);

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member.buyMembership(club, membership);

        member.registerToTrainingSession(judoSession);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, judoSession).registerMemberToSession(member, judoSession);
        });
        assertEquals(RegistrationException.Reason.MEMBER_ALREADY_REGISTERED, exception.getReason());
    }

    @Test
    void testValidateMemberBelongsToClubTrainingSessionBelongsTo() throws RegistrationException {
        SportsClub judoClub = new SportsClub("Judo Club");
        SportsClub jogaClub = new SportsClub("Joga Club");

        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        judoClub.addTrainer(generalTrainer);

        Member member = new Member("Member", true);
        jogaClub.addMember(member);
        member.addClub(jogaClub);

        Training judoTraining = new Training("Judo", JUDO, generalTrainer);
        Training jogaTraining = new Training("Joga", JOGA, generalTrainer);

        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(generalTrainer));
        TrainingSession jogaSession = new TrainingSession("Joga", JOGA, Optional.of(generalTrainer));
        judoSession.setMaxParticipants(10);
        jogaSession.setMaxParticipants(10);
        judoClub.addTraining(judoTraining);
        jogaClub.addTraining(jogaTraining);

        judoTraining.addTrainingSession(judoSession);
        jogaTraining.addTrainingSession(jogaSession);

        Validator validator = new Validator(member, judoSession);
        assertFalse(validator.validateMemberBelongsToClubTrainingSessionBelongsTo(member, judoSession));
    }

    @Test
    void testCanDeregisterMemberIsRegisteredRemovesUserFromSession()
            throws RegistrationException, TransactionException {
        SportsClub sportsClub = new SportsClub("Joga Club");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        sportsClub.addTrainer(trainer);

        Training training = new Training("Judo", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(4);

        sportsClub.addTraining(training);
        training.addTrainingSession(judoSession);

        Member member = new Member("Member", true);
        sportsClub.addMember(member);
        member.addClub(sportsClub);

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(sportsClub)
                .build();

        member.buyMembership(sportsClub, membership);

        member.registerToTrainingSession(judoSession);
        member.deregisterFromTrainingSession(judoSession);

        assertFalse(judoSession.getParticipants().contains(member));
    }

    @Test
    void testCanDeregisterMemberIsRegisteredRemovesFromMembersSessions()
            throws RegistrationException, TransactionException {
        SportsClub sportsClub = new SportsClub("Joga Club");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        sportsClub.addTrainer(trainer);

        Training training = new Training("Judo", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(4);

        sportsClub.addTraining(training);
        training.addTrainingSession(judoSession);

        Member member = new Member("Member", true);
        sportsClub.addMember(member);
        member.addClub(sportsClub);

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(sportsClub)
                .build();

        member.buyMembership(sportsClub, membership);

        member.registerToTrainingSession(judoSession);
        member.deregisterFromTrainingSession(judoSession);

        assertFalse(member.getRegisteredTrainingSessions().contains(judoSession));
    }

    @Test
    void testCanDeregisterNotMemberOfTrainingSessionReturnsFalse() {
        SportsClub sportsClub = new SportsClub("Joga Club");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        sportsClub.addTrainer(trainer);

        Training training = new Training("Judo", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(4);

        sportsClub.addTraining(training);
        training.addTrainingSession(judoSession);

        Member member = new Member("Member", true);
        sportsClub.addMember(member);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, judoSession).deregisterMemberFromSession(member, judoSession);
        });
        assertEquals(RegistrationException.Reason.MEMBER_NOT_REGISTERED, exception.getReason());
    }

    @Test
    void testDeregisterMemberSuccessful() throws RegistrationException, TransactionException {
        SportsClub sportsClub = new SportsClub("Joga Club");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        sportsClub.addTrainer(trainer);

        Training judoTraining = new Training("Judo", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));

        judoSession.setMaxParticipants(4);

        judoTraining.addTrainingSession(judoSession);
        sportsClub.addTraining(judoTraining);

        Member member1 = new Member("Member1", true);
        Member member2 = new Member("Member2", true);

        sportsClub.addMember(member1);
        member1.addClub(sportsClub);
        sportsClub.addMember(member2);
        member2.addClub(sportsClub);

        member1.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        member2.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(sportsClub)
                .build();

        member1.buyMembership(sportsClub, membership);
        member2.buyMembership(sportsClub, membership);


        member1.registerToTrainingSession(judoSession);
        member2.registerToTrainingSession(judoSession);

        member1.deregisterFromTrainingSession(judoSession);
        assertEquals(1, judoSession.getParticipants().size());
    }

    @Test
    void testRegisterToTrainingSessionIsNotMemberOfClubSessionBelongsTo()
            throws TransactionException {
        SportsClub judoClub = new SportsClub("Judo Club");
        SportsClub jogaClub = new SportsClub("Joga Club");

        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        judoClub.addTrainer(trainer);
        jogaClub.addTrainer(trainer);

        Training judoTraining = new Training("Judo", JUDO, trainer);
        Training jogaTraining = new Training("Joga", JOGA, trainer);

        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(4);
        TrainingSession jogaSession = new TrainingSession("Joga", JOGA, Optional.of(trainer));
        jogaSession.setMaxParticipants(4);

        judoClub.addTraining(judoTraining);
        jogaClub.addTraining(jogaTraining);

        Member member = new Member("Member", true);
        jogaClub.addMember(member);
        member.addClub(jogaClub);

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(jogaClub)
                .build();

        member.buyMembership(jogaClub, membership);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, judoSession).registerMemberToSession(member, judoSession);
        });
        assertEquals(RegistrationException.Reason.MEMBER_NOT_PART_OF_SESSIONS_CLUB, exception.getReason());
    }

    @Test
    void testRegisterToTrainingSessionIsNotMemberOfAnyClubs() throws RegistrationException {
        SportsClub judoClub = new SportsClub("Judo Club");
        SportsClub jogaClub = new SportsClub("Joga Club");

        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        judoClub.addTrainer(trainer);
        jogaClub.addTrainer(trainer);

        Training judoTraining = new Training("Judo", JUDO, trainer);
        Training jogaTraining = new Training("Joga", JOGA, trainer);

        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(4);
        TrainingSession jogaSession = new TrainingSession("Joga", JOGA, Optional.of(trainer));
        jogaSession.setMaxParticipants(4);

        judoClub.addTraining(judoTraining);
        jogaClub.addTraining(jogaTraining);

        Member member = new Member("Member", true);
        jogaClub.addMember(member);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, judoSession).registerMemberToSession(member, judoSession);
        });
        assertEquals(RegistrationException.Reason.MEMBER_NOT_PART_OF_ANY_CLUBS, exception.getReason());
    }
}
