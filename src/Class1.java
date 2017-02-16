import java.text.NumberFormat;
import java.util.Locale;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Alex Janousek
 *
 *         This applications purpose is to give a income estimate based on
 *         hourly pay and hours worked before taxes are deducted.
 *
 *         This application takes into account overtime after 40 hours and gives
 *         you time and a half for every hour worked after 40.
 */

public class Class1 extends Application {
	/**
	 * @uml.property  name="wageForWeek"
	 */
	int wageForWeek;
	/**
	 * @uml.property  name="wageForMonth"
	 */
	int wageForMonth;
	/**
	 * @uml.property  name="wageForYear"
	 */
	int wageForYear;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Income Estimator");

		BorderPane bp1 = new BorderPane();

		VBox pane1 = new VBox(10);
		HBox pane2 = new HBox(10);
		HBox pane3 = new HBox(10);

		HBox hbox1 = new HBox(15);

		Label hourlyWage = new Label("   Hourly Wage: ");
		hourlyWage.setFont(new Font("Times New Roman", 15));

		TextField wage = new TextField("Enter Wage Here");
		wage.setPromptText("Enter Wage Here");

		hbox1.getChildren().addAll(hourlyWage, wage);

		HBox hbox2 = new HBox(10);

		Label weeklyHours = new Label("   Weekly Hours: ");
		weeklyHours.setFont(new Font("Times New Roman", 15));

		TextField hours = new TextField();
		hours.setPromptText("Enter Hours Here");

		hbox2.getChildren().addAll(weeklyHours, hours);

		Button calculate = new Button("Calculate");
		calculate.setFont(new Font("Times New Roman", 20));

		HBox hbox3 = new HBox(10);
		Label overTime = new Label("Overtime past 40 hours is taken into consideration at time-and-a-half pay.");

		overTime.setWrapText(true);
		overTime.setFont(new Font("Times New Roman", 15));
		hbox3.getChildren().add(overTime);

		HBox hbox4 = new HBox(10);
		Label warningLabel = new Label();

		hbox4.getChildren().add(warningLabel);

		pane1.getChildren().add(hbox4);

		calculate.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				try {

					pane3.getChildren().clear();

					String hourlyWage = wage.getText();
					String weeklyHours = hours.getText();

					int payForYear = calculatePayForYear(hourlyWage, weeklyHours);
					int payForMonth = calculatePayForMonth(hourlyWage, weeklyHours);
					int payForWeek = calculatePayForWeek(hourlyWage, weeklyHours);

					Label data = new Label();
					data.setWrapText(true);
					data.setFont(new Font("Times New Roman", 25));
					data.setTextFill(Color.web("#000000"));
					data.setText("Yearly Pay:   $" + numberFormat(payForYear) + ".00" + "\nMonthly Pay: $"
							+ numberFormat(payForMonth) + ".00" + "\nWeekly Pay:  $" + numberFormat(payForWeek)
							+ ".00");

					pane3.getChildren().add(data);

				} catch (Exception e) {

					warningLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
					warningLabel.setTextFill(Color.web("#ff0000"));
					warningLabel.setText("Please Input Only Integers!");

					FadeTransition ft = new FadeTransition(Duration.millis(3000), warningLabel);
					ft.setFromValue(1.0);
					ft.setToValue(0);
					ft.setAutoReverse(true);
					ft.play();
				}
			}
		});

		pane1.setAlignment(Pos.CENTER);
		pane2.setAlignment(Pos.CENTER);
		pane3.setAlignment(Pos.CENTER);

		pane1.getChildren().addAll(hbox1, hbox2, hbox3);
		pane2.getChildren().addAll(calculate);

		bp1.setTop(pane1);
		bp1.setCenter(pane2);
		bp1.setBottom(pane3);
		bp1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		Scene scene1 = new Scene(bp1, 350, 300);

		primaryStage.setScene(scene1);
		primaryStage.show();

		primaryStage.show();

	}

	public String numberFormat(int pay) {

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		String numberAsString = numberFormat.format(pay);

		return numberAsString;
	}

	public int calculatePayForYear(String hourlyWage, String weeklyHours) {

		int weeklyHoursInt = Integer.parseInt(weeklyHours);
		int hourlyWageInt = Integer.parseInt(hourlyWage);

		if (weeklyHoursInt > 40) {

			int difference = weeklyHoursInt - 40;
			int overTime = (((hourlyWageInt / 2) + hourlyWageInt)) * difference;
			this.wageForYear = ((hourlyWageInt * 40 * 52) + overTime * 52);

		} else {

			this.wageForYear = Integer.parseInt(hourlyWage) * Integer.parseInt(weeklyHours) * 52;
		}

		return wageForYear;

	}

	public int calculatePayForMonth(String hourlyWage, String weeklyHours) {

		int weeklyHoursInt = Integer.parseInt(weeklyHours);
		int hourlyWageInt = Integer.parseInt(hourlyWage);

		if (weeklyHoursInt > 40) {

			int difference = weeklyHoursInt - 40;
			int overTime = (((hourlyWageInt / 2) + hourlyWageInt)) * difference;
			this.wageForMonth = ((hourlyWageInt * 40 * 4) + overTime * 4);

		} else {

			this.wageForMonth = Integer.parseInt(hourlyWage) * Integer.parseInt(weeklyHours) * 4;
		}

		return wageForMonth;

	}

	public int calculatePayForWeek(String hourlyWage, String weeklyHours) {

		int weeklyHoursInt = Integer.parseInt(weeklyHours);
		int hourlyWageInt = Integer.parseInt(hourlyWage);

		if (weeklyHoursInt > 40) {

			int difference = weeklyHoursInt - 40;
			int overTime = (((hourlyWageInt / 2) + hourlyWageInt)) * difference;
			this.wageForWeek = ((hourlyWageInt * 40) + overTime);

		} else {

			this.wageForWeek = Integer.parseInt(hourlyWage) * Integer.parseInt(weeklyHours);
		}

		return wageForWeek;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
