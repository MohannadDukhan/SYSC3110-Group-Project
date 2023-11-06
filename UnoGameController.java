import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UnoGameController  implements ActionListener {
    UnoGame game;
    public UnoGameController(UnoGame game) {
        super();
        this.game = game;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Player currentPlayer = game.getCurrentPlayer();
        String clickedButton = e.getActionCommand();
        if(clickedButton == "Draw Card"){
            game.handleDrawCard(currentPlayer);
        }
        if(clickedButton == "Next Player"){
            currentPlayer = game.getNextCurrentPlayer();
        }

    }
}
