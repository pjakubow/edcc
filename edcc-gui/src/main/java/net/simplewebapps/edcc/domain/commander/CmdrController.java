package net.simplewebapps.edcc.domain.commander;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Font;
import net.simplewebapps.edcc.util.JavaFxEventSubscriber;
import net.simplewebapps.edcc.event.LoadGame;
import net.simplewebapps.edcc.event.Progress;
import net.simplewebapps.edcc.event.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

@Component
public class CmdrController implements Initializable {

    @FXML private ProgressIndicator tradeProgress;
    @FXML private ProgressIndicator explorationProgress;
    @FXML private ProgressIndicator empireProgress;
    @FXML private ProgressIndicator federationProgress;
    @FXML private ProgressIndicator cqcProgress;
    @FXML private ProgressIndicator combatProgress;

    @FXML private Label cmdrName;

    @FXML private Label empireRank;
    @FXML private Label federationRank;
    @FXML private Label cqcRank;
    @FXML private Label tradeRank;
    @FXML private Label combatRank;
    @FXML private Label explorationRank;

    private JavaFxEventSubscriber eventSubscriber;

    @Autowired
    public CmdrController(JavaFxEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Font eurocaps24 = Font.loadFont(getClass().getResource("/fonts/EUROCAPS.TTF").toExternalForm(), 24);
        Font telegrama24 = Font.loadFont(getClass().getResource("/fonts/telegrama_render.otf").toExternalForm(), 24);

        cmdrName.setFont(eurocaps24);
        Arrays.asList(empireRank, federationRank, cqcRank, tradeRank, combatRank, explorationRank)
                .forEach(r -> r.setFont(telegrama24));


        eventSubscriber.addCallback(LoadGame.class, (loadGame) -> this.onLoadGame((LoadGame) loadGame));
        eventSubscriber.addCallback(Rank.class, (rank) -> this.onRank((Rank) rank));
        eventSubscriber.addCallback(Progress.class, (progress) -> this.onProgress((Progress) progress));
    }

    private void onProgress(Progress progress) {
        empireProgress.setProgress(progress.getEmpire() / 100d);
        federationProgress.setProgress(progress.getFederation() / 100d);
        cqcProgress.setProgress(progress.getCqc() / 100d);

        tradeProgress.setProgress(progress.getTrade() / 100d);
        combatProgress.setProgress(progress.getCombat() / 100d);
        explorationProgress.setProgress(progress.getExplore() / 100d);
    }

    private void onRank(Rank rank) {
        empireRank.setText(rank.getEmpire().toString());
        federationRank.setText(rank.getFederation().toString());
        cqcRank.setText(rank.getCqc().toString());

        tradeRank.setText(rank.getTrade().toString());
        combatRank.setText(rank.getCombat().toString());
        explorationRank.setText(rank.getExplore().toString());
    }

    private void onLoadGame(LoadGame loadGame) {
        cmdrName.setText(
                "CMDR " + loadGame.getCommander()
                + ", $" + NumberFormat.getInstance().format(loadGame.getCredits())
                + ", loan: $" + loadGame.getLoan()
                + ", ship: " + loadGame.getShip());

        loadGame.getGroup();
        loadGame.getShipId();
        loadGame.getGameMode();
    }
}
