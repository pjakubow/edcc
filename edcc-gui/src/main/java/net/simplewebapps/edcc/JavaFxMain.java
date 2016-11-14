/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.simplewebapps.edcc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.simplewebapps.edcc.spring.AbstractJavaFxApplicationSupport;
import net.simplewebapps.edcc.gui.RootController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaFxMain extends AbstractJavaFxApplicationSupport {

	@Value("${app.ui.title:EDCC-DUMMY}")//
	private String windowTitle;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/root.fxml"));
		fxmlLoader.setControllerFactory(getControllerFactory());
		Parent root = fxmlLoader.load();
		((RootController) fxmlLoader.getController()).setCallingStage(primaryStage);
		primaryStage.setTitle(windowTitle);
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launchApp(JavaFxMain.class, args);
	}
}
