package net.simplewebapps.edcc;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import net.simplewebapps.edcc.config.ObjectMapperConfig;
import net.simplewebapps.edcc.event.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class LogParserTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private LogParser logParser;

    @Before
    public void setUp() throws Exception {
        logParser = new LogParser(new ObjectMapperConfig().objectMapper());
    }

    @Test
    public void shouldThrowUnknownTypeExceptionOnUnmappedEvent() throws Exception {
        try {
            logParser.parseLine("{ \"timestamp\":\"2016-10-27T22:35:31Z\", \"event\":\"Nonexistent\", \"Health\":0.581245 }");
            fail("Should have thrown UnknownTypeException");
        } catch (LogParser.UnknownTypeException e) {
            assertThat(e.getType()).isEqualTo("Nonexistent");
        }
    }

    @Test
    @Parameters({
            "2016-06-20T21:30:00Z, 2016-06-20 23:30:00", // no-DST
            "2016-11-05T15:40:00Z, 2016-11-05 16:40:00"  // DST
    })
    public void shouldTakeTimezoneIntoAccount(String logTimestamp, String localTimestamp) throws Exception {
        //
        String line = "{ \"timestamp\":\"" + logTimestamp + "\", \"event\":\"Fileheader\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(format(event.getTimestamp())).isEqualTo(localTimestamp);
    }

    @Test
    public void shouldParseEventWhenFieldsInOrder() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-10-27T21:39:33Z\", \"event\":\"Fileheader\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Fileheader.class);
    }

    @Test
    public void shouldParseEventWhenAdditionalFields() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-10-27T21:39:33Z\", \"event\":\"Fileheader\", \"unknownField\":\"BOO!\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Fileheader.class);

    }

    @Test
    public void shouldParseClearSavedGameEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"ClearSavedGame\", \"Name\":\"AJRee\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(ClearSavedGame.class);
        assertThat(((ClearSavedGame) event).getName()).isEqualTo("AJRee");
    }

    @Test
    public void shouldParseNewCommanderEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"NewCommander\", \"Name\":\"AJRee\", \"Package\":\"ImperialBountyHunter\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(NewCommander.class);
        assertThat(((NewCommander) event).getName()).isEqualTo("AJRee");
        assertThat(((NewCommander) event).getStarterPackage()).isEqualTo("ImperialBountyHunter");
    }

    @Test
    public void shouldParseLoadGameEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"LoadGame\", \"Commander\":\"AJRee\", \"Ship\":\"CobraMkIII\", \"ShipID\":1, \"GameMode\":\"Group\", \"Group\":\"Le Group Name\", \"Credits\":600120, \"Loan\":0  }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(LoadGame.class);
        LoadGame loadGameEvent = (LoadGame) event;
        assertThat(loadGameEvent.getCommander()).isEqualTo("AJRee");
        assertThat(loadGameEvent.getShip()).isEqualTo("CobraMkIII");
        assertThat(loadGameEvent.getShipId()).isEqualTo(1);
        assertThat(loadGameEvent.getStartLanded()).isNull();
        assertThat(loadGameEvent.getStartDead()).isNull();
        assertThat(loadGameEvent.getGameMode()).isEqualTo(GameMode.Group);
        assertThat(loadGameEvent.getGroup()).isEqualTo("Le Group Name");
        assertThat(loadGameEvent.getCredits()).isEqualTo(600_120);
        assertThat(loadGameEvent.getLoan()).isEqualTo(0);
    }

    @Test
    public void shouldParseProgressEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"Progress\", \"Combat\":77, \"Trade\":9, \"Explore\":93, \"Empire\":0, \"Federation\":0, \"CQC\":0 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Progress.class);
        Progress progress = (Progress) event;
        assertThat(progress.getCombat()).isEqualTo(77);
        assertThat(progress.getTrade()).isEqualTo(9);
        assertThat(progress.getExplore()).isEqualTo(93);
        assertThat(progress.getEmpire()).isEqualTo(0);
        assertThat(progress.getFederation()).isEqualTo(0);
        assertThat(progress.getCqc()).isEqualTo(0);
    }

    @Test
    public void shouldParseRankEvent() throws Exception {
        // given
        String line = "{ \"timestamp\":\"2016-06-10T14:32:03Z\", \"event\":\"Rank\", \"Combat\":2, \"Trade\":2, \"Explore\":5, \"Empire\":1, \"Federation\":3, \"CQC\":0 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Rank.class);
        Rank rank = (Rank) event;
        assertThat(rank.getCombat()).isEqualTo(2);
        assertThat(rank.getTrade()).isEqualTo(2);
        assertThat(rank.getExplore()).isEqualTo(5);
        assertThat(rank.getEmpire()).isEqualTo(1);
        assertThat(rank.getFederation()).isEqualTo(3);
        assertThat(rank.getCqc()).isEqualTo(0);
    }

    @Test
    public void shouldParseReceiveTextEvent() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-10-29T22:59:55Z\", " +
                "\"event\":\"ReceiveText\", " +
                "\"From\":\"$npc_name_decorate:#name=Sergio Xisto;\", " +
                "\"From_Localised\":\"Sergio Xisto\", " +
                "\"Message\":\"$Police_Attack11;\", " +
                "\"Message_Localised\":\"Justice awaits you, perp.\", " +
                "\"Channel\":\"npc\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(ReceiveText.class);
        ReceiveText receiveText = (ReceiveText) event;
        assertThat(receiveText.getFrom()).isEqualTo("Sergio Xisto");
        assertThat(receiveText.getMessage()).isEqualTo("Justice awaits you, perp.");
        assertThat(receiveText.getChannel()).isEqualTo(Channel.npc);
    }

    @Test
    public void shouldParseMissionAcceptedEvent() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-07-26T11:36:44Z\", " +
                "\"event\":\"MissionAccepted\", " +
                "\"Faction\":\"Tsu Network\", " +
                "\"Name\":\"Mission_Collect\", " +
                "\"MissionID\":65343026, " +
                "\"Commodity\":\"$Fish_Name;\", " +
                "\"Commodity_Localised\":\"Fish\", " +
                "\"Count\":2, " +
                "\"Expiry\":\"2016-07-27T15:56:23Z\" }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionAccepted.class);
        MissionAccepted missionAccepted = (MissionAccepted) event;

        assertThat(missionAccepted.getId()).isEqualTo(65343026);
        assertThat(missionAccepted.getName()).isEqualTo("Mission_Collect");
        assertThat(missionAccepted.getFaction()).isEqualTo("Tsu Network");

        assertThat(missionAccepted.getCommodity()).isEqualTo("Fish");
        assertThat(missionAccepted.getCount()).isEqualTo(2);
        assertThat(format(missionAccepted.getExpiry())).isEqualTo("2016-07-27 17:56:23");
    }

    @Test
    public void shouldParseMissionAcceptedEventWithLocalisations() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-10-29T22:48:38Z\", " +
                "\"event\":\"MissionAccepted\", " +
                "\"Faction\":\"Elite Rebel Force\", " +
                "\"Name\":\"Mission_Assassinate\", " +
                "\"TargetType\":\"$MissionUtil_FactionTag_PirateLord;\", " +
                "\"TargetType_Localised\":\"Pirate lord\", " +
                "\"DestinationSystem\":\"NLTT 46621\", " +
                "\"DestinationStation\":\"Nusslein-Volhard Terminal\", " +
                "\"Expiry\":\"2016-10-20T22:44:46Z\", " +
                "\"PassengerType\":\"$AjreeMissionUtil_PassengerType_Tourist\", " +
                "\"PassengerType_Localised\":\"Tourist\", " +
                "\"MissionID\":46008584 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionAccepted.class);
        MissionAccepted missionAccepted = (MissionAccepted) event;

        assertThat(missionAccepted.getId()).isEqualTo(46008584);
        assertThat(missionAccepted.getName()).isEqualTo("Mission_Assassinate");
        assertThat(missionAccepted.getFaction()).isEqualTo("Elite Rebel Force");

        assertThat(missionAccepted.getTargetType()).isEqualTo("Pirate lord");
        assertThat(missionAccepted.getDestinationSystem()).isEqualTo("NLTT 46621");
        assertThat(missionAccepted.getDestinationStation()).isEqualTo("Nusslein-Volhard Terminal");
        assertThat(format(missionAccepted.getExpiry())).isEqualTo("2016-10-21 00:44:46");
        assertThat(missionAccepted.getPassengerType()).isEqualTo("Tourist");
    }

    @Test
    public void shouldParseMissionCompletedEventWithCommodityRewards() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-09-30T08:37:38Z\", " +
                "\"event\":\"MissionCompleted\", " +
                "\"Faction\":\"Maljenni Inc\", " +
                "\"Name\":\"Mission_Delivery_name\", " +
                "\"MissionID\":65347208, " +
                "\"Commodity\":\"$Cobalt_Name;\", " +
                "\"Commodity_Localised\":\"Cobalt\", " +
                "\"Count\":14, " +
                "\"DestinationSystem\":\"Maljenni\", " +
                "\"DestinationStation\":\"Bowersox Enterprise\", " +
                "\"Reward\":0, " +
                "\"CommodityReward\":[ " +
                  "{ \"Name\": \"ArticulationMotors\", " +
                    "\"Count\": 2 }, " +
                  "{ \"Name\": \"Fish\", " +
                    "\"Count\": 6 } " +
                "] }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionCompleted.class);
        MissionCompleted missionCompleted = (MissionCompleted) event;
        assertThat(missionCompleted.getId()).isEqualTo(65347208);
        assertThat(missionCompleted.getName()).isEqualTo("Mission_Delivery_name");
        assertThat(missionCompleted.getFaction()).isEqualTo("Maljenni Inc");

        assertThat(missionCompleted.getCommodity()).isEqualTo("Cobalt");
        assertThat(missionCompleted.getCount()).isEqualTo(14);
        assertThat(missionCompleted.getDestinationSystem()).isEqualTo("Maljenni");
        assertThat(missionCompleted.getDestinationStation()).isEqualTo("Bowersox Enterprise");
        assertThat(missionCompleted.getReward()).isEqualTo(0);

        List<CommodityReward> commodityRewards = missionCompleted.getCommodityRewards();
        assertThat(commodityRewards).hasSize(2);
        assertThat(commodityRewards.get(0).getName()).isEqualTo("ArticulationMotors");
        assertThat(commodityRewards.get(0).getCount()).isEqualTo(2);
        assertThat(commodityRewards.get(1).getName()).isEqualTo("Fish");
        assertThat(commodityRewards.get(1).getCount()).isEqualTo(6);
    }

    @Test
    public void shouldParseMissionCompletedEventWithPermitRewards() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-09-30T08:37:38Z\", " +
                "\"event\":\"MissionCompleted\", " +
                "\"Faction\":\"Maljenni Inc\", " +
                "\"Name\":\"Mission_Delivery_name\", " +
                "\"MissionID\":65347208, " +
                "\"Commodity\":\"$Cobalt_Name;\", " +
                "\"Commodity_Localised\":\"Cobalt\", " +
                "\"Count\":14, " +
                "\"DestinationSystem\":\"Maljenni\", " +
                "\"DestinationStation\":\"Bowersox Enterprise\", " +
                "\"Reward\":1000000, " +
                "\"PermitsAwarded\":[ \"Sol\" ] }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionCompleted.class);
        MissionCompleted missionCompleted = (MissionCompleted) event;
        assertThat(missionCompleted.getId()).isEqualTo(65347208);
        assertThat(missionCompleted.getName()).isEqualTo("Mission_Delivery_name");
        assertThat(missionCompleted.getFaction()).isEqualTo("Maljenni Inc");

        assertThat(missionCompleted.getCommodity()).isEqualTo("Cobalt");
        assertThat(missionCompleted.getCount()).isEqualTo(14);
        assertThat(missionCompleted.getDestinationSystem()).isEqualTo("Maljenni");
        assertThat(missionCompleted.getDestinationStation()).isEqualTo("Bowersox Enterprise");
        assertThat(missionCompleted.getReward()).isEqualTo(1_000_000);

        List<String> permitsAwarded = missionCompleted.getPermitsAwarded();
        assertThat(permitsAwarded).hasSize(1);
        assertThat(permitsAwarded.get(0)).isEqualTo("Sol");
    }

    @Test
    public void shouldParseMissionCompletedEventWithDonation() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-10-29T22:07:44Z\", " +
                "\"event\":\"MissionCompleted\", " +
                "\"Faction\":\"Future of Altair\", " +
                "\"Name\":\"Mission_AltruismCredits_name\", " +
                "\"MissionID\":45996566, " +
                "\"Donation\":575000 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionCompleted.class);
        MissionCompleted missionCompleted = (MissionCompleted) event;
        assertThat(missionCompleted.getId()).isEqualTo(45996566);
        assertThat(missionCompleted.getName()).isEqualTo("Mission_AltruismCredits_name");
        assertThat(missionCompleted.getFaction()).isEqualTo("Future of Altair");

        assertThat(missionCompleted.getDonation()).isEqualTo(575_000);
    }

    @Test
    public void shouldParseMissionAbandonedEvent() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-06-10T14:32:03Z\", " +
                "\"event\":\"MissionAbandoned\", " +
                "\"Name\":\"Mission_Collect_name\", " +
                "\"MissionID\":65343025 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionAbandoned.class);
        MissionAbandoned missionAbandoned = (MissionAbandoned) event;
        assertThat(missionAbandoned.getId()).isEqualTo(65343025);
        assertThat(missionAbandoned.getName()).isEqualTo("Mission_Collect_name");
    }

    @Test
    public void shouldParseMissionFailedEvent() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-06-10T14:32:03Z\", " +
                "\"event\":\"MissionFailed\", " +
                "\"Name\":\"Mission_Collect_name\", " +
                "\"MissionID\":65343025 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(MissionFailed.class);
        MissionFailed missionFailed = (MissionFailed) event;
        assertThat(missionFailed.getId()).isEqualTo(65343025);
        assertThat(missionFailed.getName()).isEqualTo("Mission_Collect_name");
    }

    @Test
    public void shouldParseScanEventForAPlanet() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-09-22T10:40:44Z\", " +
                "\"event\":\"Scan\", " +
                "\"BodyName\":\"Bei Dou Sector JH-V b2-1 1\"," +
                "\"DistanceFromArrivalLS\":392.607605, " +
                "\"TidalLock\":false, " +
                "\"TerraformState\":\"\", " +
                "\"PlanetClass\":\"Icy body\", " +
                "\"Atmosphere\":\"thin neon rich atmosphere\", " +
                "\"Volcanism\":\"\", " +
                "\"MassEM\":0.190769," +
                "\"Radius\":4412562.000000, " +
                "\"SurfaceGravity\":3.905130, " +
                "\"SurfaceTemperature\":64.690628, " +
                "\"SurfacePressure\":321.596558, " +
                "\"Landable\":false, " +
                "\"SemiMajorAxis\":117704065024.000000, " +
                "\"Eccentricity\":0.000033, " +
                "\"Periapsis\":5.692884, " +
                "\"OrbitalPeriod\":43704092.000000, " +
                "\"RotationPeriod\":104296.351563 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Scan.class);
        Scan scan = (Scan) event;
        assertThat(scan.getName()).isEqualTo("Bei Dou Sector JH-V b2-1 1");
        assertThat(scan.getDistanceFromArrival()).isEqualTo(392.607605);
        assertThat(scan.getTidalLocked()).isEqualTo(false);
        assertThat(scan.getTerraformState()).isEqualTo("");
        assertThat(scan.getPlanetClass()).isEqualTo("Icy body");
        assertThat(scan.getAtmosphere()).isEqualTo("thin neon rich atmosphere");
        assertThat(scan.getVolcanism()).isEqualTo("");
        assertThat(scan.getMassEM()).isEqualTo(0.190769);
        assertThat(scan.getStarType()).isNull();
        assertThat(scan.getStellarMass()).isNull();
        assertThat(scan.getRadius()).isEqualTo(4412562.000000);
        assertThat(scan.getSurfaceGravity()).isEqualTo(3.905130);
        assertThat(scan.getSurfaceTemperature()).isEqualTo(64.690628);
        assertThat(scan.getSurfacePressure()).isEqualTo(321.596558);
        assertThat(scan.getAbsoluteMagnitude()).isNull();
        assertThat(scan.getAge()).isNull();
        assertThat(scan.getLandable()).isEqualTo(false);
        assertThat(scan.getSemiMajorAxis()).isEqualTo(117704065024.000000);
        assertThat(scan.getEccentricity()).isEqualTo(0.000033);
        assertThat(scan.getPeriapsis()).isEqualTo(5.692884);
        assertThat(scan.getOrbitalPeriod()).isEqualTo(43704092.000000);
        assertThat(scan.getRotationPeriod()).isEqualTo(104296.351563);
        assertThat(scan.getMaterials()).isNotNull();
        assertThat(scan.getMaterials()).isEmpty();
        assertThat(scan.getRings()).isNotNull();
        assertThat(scan.getRings()).isEmpty();
    }

    @Test
    public void shouldParseScanEventForAPlanetWithMaterials() throws Exception {
        String line = "{ " +
                "\"timestamp\":\"2016-10-27T22:29:49Z\", " +
                "\"event\":\"Scan\", " +
                "\"BodyName\":\"LP 36-115 A 1 a\", " +
                "\"DistanceFromArrivalLS\":1362.646362, " +
                "\"TidalLock\":true, " +
                "\"TerraformState\":\"\", " +
                "\"PlanetClass\":\"Icy body\", " +
                "\"Atmosphere\":\"\", " +
                "\"Volcanism\":\"major water geysers volcanism\", " +
                "\"MassEM\":0.000200, " +
                "\"Radius\":517357.781250, " +
                "\"SurfaceGravity\":0.297809, " +
                "\"SurfaceTemperature\":95.017609, " +
                "\"SurfacePressure\":0.000000, " +
                "\"Landable\":true, " +
                "\"Materials\":{ " +
                    "\"sulphur\":27.6, " +
                    "\"carbon\":23.2, " +
                    "\"phosphorus\":14.8, " +
                    "\"iron\":12.6, " +
                    "\"nickel\":9.5, " +
                    "\"manganese\":5.2, " +
                    "\"vanadium\":3.1, " +
                    "\"arsenic\":1.6, " +
                    "\"tellurium\":0.9, " +
                    "\"niobium\":0.9, " +
                    "\"tungsten\":0.7 }, " +
                "\"SemiMajorAxis\":1307086.875000, " +
                "\"Eccentricity\":0.043846, " +
                "\"OrbitalInclination\":-7.219698, " +
                "\"Periapsis\":208.774216, " +
                "\"OrbitalPeriod\":75015.984375, " +
                "\"RotationPeriod\":109376.093750 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Scan.class);
        Scan scan = (Scan) event;
        assertThat(scan.getLandable()).isEqualTo(true);
        assertThat(scan.getMaterials().size()).isEqualTo(11);
        assertThat(scan.getMaterials().get("phosphorus")).isEqualTo(14.8);
    }

    @Test
    public void shouldParseScanEventForAStar() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-10-28T18:27:33Z\", " +
                "\"event\":\"Scan\", " +
                "\"BodyName\":\"Betelgeuse\", " +
                "\"DistanceFromArrivalLS\":0.000000, " +
                "\"StarType\":\"M_RedSuperGiant\", " +
                "\"StellarMass\":0.257813, " +
                "\"Radius\":445379215360.000000, " +
                "\"AbsoluteMagnitude\":-5.468781, " +
                "\"Age_MY\":10402, " +
                "\"SurfaceTemperature\":2445.000000, " +
                "\"RotationPeriod\":242753520.000000 }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Scan.class);
        Scan scan = (Scan) event;
        assertThat(scan.getName()).isEqualTo("Betelgeuse");
        assertThat(scan.getDistanceFromArrival()).isEqualTo(0.000000);
        assertThat(scan.getTidalLocked()).isNull();
        assertThat(scan.getTerraformState()).isNull();
        assertThat(scan.getPlanetClass()).isNull();
        assertThat(scan.getAtmosphere()).isNull();
        assertThat(scan.getVolcanism()).isNull();
        assertThat(scan.getMassEM()).isNull();
        assertThat(scan.getStarType()).isEqualTo("M_RedSuperGiant");
        assertThat(scan.getStellarMass()).isEqualTo(0.257813);
        assertThat(scan.getRadius()).isEqualTo(445379215360.000000);
        assertThat(scan.getAbsoluteMagnitude()).isEqualTo(-5.468781);
        assertThat(scan.getAge()).isEqualTo(10402);
        assertThat(scan.getSurfaceGravity()).isNull();
        assertThat(scan.getSurfaceTemperature()).isEqualTo(2445.000000);
        assertThat(scan.getSurfacePressure()).isNull();
        assertThat(scan.getLandable()).isNull();
        assertThat(scan.getSemiMajorAxis()).isNull();
        assertThat(scan.getEccentricity()).isNull();
        assertThat(scan.getPeriapsis()).isNull();
        assertThat(scan.getOrbitalPeriod()).isNull();
        assertThat(scan.getRotationPeriod()).isEqualTo(242753520.000000);
        assertThat(scan.getMaterials()).isNotNull();
        assertThat(scan.getMaterials()).isEmpty();
        assertThat(scan.getRings()).isNotNull();
        assertThat(scan.getRings()).isEmpty();
    }

    @Test
    public void shouldParseScanEventForARingedPlanet() throws Exception {
        // given
        String line = "{ " +
                "\"timestamp\":\"2016-10-27T22:24:19Z\", " +
                "\"event\":\"Scan\", " +
                "\"BodyName\":\"LP 36-115 A 1\", " +
                "\"DistanceFromArrivalLS\":1362.164795, " +
                "\"TidalLock\":false, " +
                "\"TerraformState\":\"\", " +
                "\"PlanetClass\":\"Sudarsky class I gas giant\", " +
                "\"Atmosphere\":\"\", " +
                "\"Volcanism\":\"\", " +
                "\"MassEM\":96.031731, " +
                "\"Radius\":60186644.000000, " +
                "\"SurfaceGravity\":10.566322, " +
                "\"SurfaceTemperature\":112.272552, " +
                "\"SurfacePressure\":0.000000, " +
                "\"Landable\":false, " +
                "\"SemiMajorAxis\":408490409984.000000, " +
                "\"Eccentricity\":0.000605, " +
                "\"OrbitalInclination\":-0.320333, " +
                "\"Periapsis\":101.359970, " +
                "\"OrbitalPeriod\":236128960.000000, " +
                "\"RotationPeriod\":110902.265625, " +
                "\"Rings\":[ " +
                    "{ " +
                        "\"Name\":\"LP 36-115 A 1 A Ring\", " +
                        "\"RingClass\":\"eRingClass_Rocky\", " +
                        "\"MassMT\":4.5813e+10, " +
                        "\"InnerRad\":1.1684e+08, " +
                        "\"OuterRad\":1.234e+08 " +
                    "}, " +
                    "{ " +
                        "\"Name\":\"LP 36-115 A 1 B Ring\", " +
                        "\"RingClass\":\"eRingClass_Icy\", " +
                        "\"MassMT\":2.9625e+11, " +
                        "\"InnerRad\":1.235e+08, " +
                        "\"OuterRad\":1.5942e+08 " +
                    "} " +
                "] }";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(Scan.class);
        Scan scan = (Scan) event;
        assertThat(scan.getName()).isEqualTo("LP 36-115 A 1");
        assertThat(scan.getRings().size()).isEqualTo(2);

        assertThat(scan.getRings().get(0).getName()).isEqualTo("LP 36-115 A 1 A Ring");
        assertThat(scan.getRings().get(0).getRingClass()).isEqualTo("eRingClass_Rocky");
        assertThat(scan.getRings().get(0).getMass()).isEqualTo(4.5813e+10);
        assertThat(scan.getRings().get(0).getInnerRadius()).isEqualTo(1.1684e+08);
        assertThat(scan.getRings().get(0).getOuterRadius()).isEqualTo(1.234e+08);

        assertThat(scan.getRings().get(1).getName()).isEqualTo("LP 36-115 A 1 B Ring");
        assertThat(scan.getRings().get(1).getRingClass()).isEqualTo("eRingClass_Icy");
        assertThat(scan.getRings().get(1).getMass()).isEqualTo(2.9625e+11);
        assertThat(scan.getRings().get(1).getInnerRadius()).isEqualTo(1.235e+08);
        assertThat(scan.getRings().get(1).getOuterRadius()).isEqualTo(1.5942e+08);
    }

    /*

    @Test
    public void shouldParseEvent() throws Exception {
        // given
        String line = "";

        // when
        Event event = logParser.parseLine(line);

        // then
        assertThat(event).isInstanceOf(ClearSavedGame.class);
        ClearSavedGame clearSavedGame = (ClearSavedGame) event;
        assertThat(clearSavedGame.getName()).isEqualTo("");
    }
    */

    private String format(Date timestamp) {
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.of("Europe/Warsaw"));
        return zonedDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}