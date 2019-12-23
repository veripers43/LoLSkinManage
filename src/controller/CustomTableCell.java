package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.SkinVO;




public class CustomTableCell<S> extends TableCell<S,Image>{

	
	    final ImageView imageView = new ImageView();

	    
	    CustomTableCell() {
	        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	    }

	    
	    protected void updateItem(Image item, boolean empty) {
	        super.updateItem(item, empty);
	       // System.out.println("aaaaaa="+item.toString());
	    
	        if (empty || item == null) {
	            imageView.setImage(null);
	            setText(null);
	            setGraphic(null);
	            imageView.setFitHeight(250);
	            imageView.setFitWidth(170);
	            imageView.preserveRatioProperty();
	        }
	       
	        imageView.setImage(item);
	        setGraphic(imageView);
	        
	    }
	    
	}