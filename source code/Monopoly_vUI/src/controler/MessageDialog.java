package controler;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MessageDialog {
	private Button bt;
	private final Stage stage;
	
	public MessageDialog(final Stage primaryStage,String text,int width,int height){
		stage = new Stage();
		//Initialize the Stage with type of modal
		stage.initModality(Modality.APPLICATION_MODAL);
		//Set the owner of the Stage 
		stage.initOwner(primaryStage);
		stage.setTitle("Message");
		
		Group root = new Group();
		Scene scene = new Scene(root,width,height);
		stage.setScene(scene);
		
		Text t = new Text(text);
		t.setLayoutX(10);
		t.setLayoutY(20);
		root.getChildren().add(t);
		
		bt = new Button("OK");
		bt.setLayoutX(width/2);
		bt.setLayoutY(height-30);
		root.getChildren().add(bt);
		bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
			
		});
		stage.show();
	}
	
	public Button getBt(){
		return this.bt;
	}

	public void setOnCloseRequest(EventHandler<WindowEvent> eventHandler) {
		stage.setOnCloseRequest(eventHandler);
	}
}
