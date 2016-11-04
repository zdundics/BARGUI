
//Zach Dundics
// 16081102

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import static javafx.scene.chart.XYChart.*;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.Random;

public class DundicsP2 extends Application {
	public static void main(String[] args) {
		DundicsP2.launch(args);

	}

	private static final int BAR_COUNT = 14, MAX_BAR_HEIGHT = 50;

	private static final String COLOR_ACTIVE = "-fx-bar-fill: #f64", COLOR_INITIAL = "-fx-bar-fill: #888",
			COLOR_FINALIZED = "-fx-bar-fill: #3cf";
	// COLOR_ACTIVEREVERSAL = "-fx-bar-fill: #f80",

	private static int DELAY_MILLIS = 700;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Sorting Animations");
		stage.setWidth(800);
		stage.setHeight(600);

		final BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));

		final BarChart<String, Number> chart = new BarChart<>(new CategoryAxis(), new NumberAxis(0, MAX_BAR_HEIGHT, 0));
		chart.setLegendVisible(false);
		chart.getYAxis().setTickLabelsVisible(false);
		chart.getYAxis().setOpacity(0);
		chart.getXAxis().setTickLabelsVisible(false);
		chart.getXAxis().setOpacity(0);
		chart.setHorizontalGridLinesVisible(false);
		chart.setVerticalGridLinesVisible(false);

		bars = new ArrayList<Data<String, Number>>();
		final Series<String, Number> data = new Series<>();
		chart.getData().add(data);
		for (int i = 0; i < BAR_COUNT; i++) {
			bars.add(new Data<>(Integer.toString(i + 1), 1));
			data.getData().add(bars.get(i));
			paint(i, COLOR_INITIAL);
		}
		pane.setCenter(chart);

		inputs = new FlowPane();
		inputs.setHgap(5);
		inputs.setVgap(5);
		createButton("Default Delay", () -> DefaultDelay(700)); // made buttons
																// instead of
																// radio buttons
																// for delay.
		createButton("Delay: 1400 ms", () -> Delay1400ms(700));
		createButton("Delay: 350 ms", () -> Delay350ms(700));
		createButton("Delay: 50 ms", () -> Delay50ms(700));
		createButton("Insertion Sort", () -> insertionSortAll());
		createButton("Selection Sort", () -> selectionSortAll2());
		createButton("Bubble Sort", () -> bubbleSortAll());
		createButton("Reversal", () -> reversalAll());
		createButton("Randomize", () -> randomizeAll());
		createButton("Swap Halves", () -> swapHalves());
		pane.setBottom(inputs);

		stage.setScene(new Scene(pane));
		stage.show();

		Platform.runLater(() -> randomizeAll());
	}

	private ArrayList<Data<String, Number>> bars;
	private FlowPane inputs;

	private void DefaultDelay(int DELAY_MILLIS) {
		DELAY_MILLIS = DELAY_MILLIS * 1;

	}

	private void Delay1400ms(int DELAY_MILLIS) {
		DELAY_MILLIS = DELAY_MILLIS * 2;
	}

	private void Delay350ms(int DELAY_MILLIS) {
		DELAY_MILLIS = DELAY_MILLIS / 2;
	}

	private void Delay50ms(int Delay_MILLIS) {
		DELAY_MILLIS = DELAY_MILLIS / 14;

	}

	private void createButton(String label, Runnable method) {
		final Button test = new Button(label);
		test.setOnAction(event -> new Thread(() -> {
			Platform.runLater(() -> inputs.setDisable(true));
			method.run();
			Platform.runLater(() -> inputs.setDisable(false));
		}).start());
		inputs.getChildren().add(test);
	}

	// CHART ACCESSORS AND MUTATORS

	private void assign(int index, int value) {
		bars.get(index).setYValue(value);
	}

	private int retrieve(int index) {
		return (int) bars.get(index).getYValue();
	}

	// ANIMATION CONTROLS

	private void paint(int index, final String style) {
		Platform.runLater(() -> {
			bars.get(index).getNode().setStyle(style);
		});
	}

	private void paintAll(final String style) {
		Platform.runLater(() -> {
			for (int i = 0; i < BAR_COUNT; i++)
				paint(i, style);
		});
	}

	private void delay() {
		try {
			Thread.sleep(DELAY_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ALGORITHMS FOR BUTTONS

	private void randomizeAll() {
		Random random = new Random();
		for (int i = 0; i < BAR_COUNT; i++) {
			assign(i, random.nextInt(MAX_BAR_HEIGHT) + 1);
		}
	}

	private void reversalAll() {
		int j = BAR_COUNT;
		for (int i = 0; i < j - 1; i++) {
			int k = j - i - 1;
			if (i < k) {

				// swap(i,k, BAR_COUNT); //easier for me to use swap method for
				// bubble sort, wasn't working without it
				paint(i, COLOR_ACTIVE);
				paint(k, COLOR_ACTIVE);

				int temp = retrieve(i);
				swap(i, k, temp);
				// int temp = retrieve(i);
				// assign(i, retrieve(k));
				// assign(k, temp);
				delay();
				// delay();
				// paint(i, COLOR_INITIAL);
				// paint(k, COLOR_INITIAL);
				// delay();
				paint(i, COLOR_FINALIZED);
				paint(k, COLOR_FINALIZED);
				delay();

			}
			// paintAll(COLOR_INITIAL);
			// int k1 = i+1;
			// String temp = words[i];
			// words[k1] = words[i];
			// words[i] = temp;
		}
		paintAll(COLOR_INITIAL);
	}
	// System.out.println(Arrays.toString(words)); //has to be outside for loop

	public void swap(int i, int k, int barCount) { // method for swapping
													// numbers
		int temp = retrieve(i);
		assign(i, retrieve(k));
		assign(k, temp);
		// k = temp;
	}

	public void selectionSortAll2() {
		int i, j, first, temp;
		for (i = BAR_COUNT - 1; i > 0; i--) {
			first = 0;
			for (j = 1; j <= 1; j++) {
				if (retrieve(j) < retrieve(first))
					first = j;

			}
			paint(i, COLOR_ACTIVE);
			paint(first, COLOR_ACTIVE);

			temp = retrieve(first);
			assign(first, retrieve(i));
			assign(i, temp);

			paint(i, COLOR_FINALIZED);
			paint(first, COLOR_FINALIZED);
			// temp = retrieve(first);
			// assign(first, retrieve(i));
			// assign(i,temp);
		}

	}

	public void selectionSortAll() { // not sure what's happening with this
										// code. May have to run twice.
		for (int i = 0; i < BAR_COUNT - 1; i++) {
			// int k = i + 1;
			int index = i;
			for (int j = i + 1; j < BAR_COUNT; j++) {
				// int k = j + 1;
				if (retrieve(j) > retrieve(index)) {
					index = j;
					paint(i, COLOR_ACTIVE);
					paint(index, COLOR_ACTIVE);

					int temp = retrieve(i);
					assign(i, retrieve(index));
					assign(index, temp);

					delay();

					paint(i, COLOR_FINALIZED);
					paint(index, COLOR_FINALIZED);

				}
			}
			// paint(i, COLOR_ACTIVE);

			// paint(index, COLOR_ACTIVE);
			// delay();

			// int temp = retrieve(i);
			// assign(i, retrieve(index));
			// assign(index, temp);

			// delay();

			// paint(i, COLOR_FINALIZED);
			// paint(index, COLOR_FINALIZED);

			// index = j;
			// int temp = index;
			// index = i;
			// i = temp;
			// paintAll(COLOR_INITIAL);
		}
		paintAll(COLOR_INITIAL);
	}

	public void insertionSortAll() {
		int j;
		int temp;
		int i;
		for (i = 1; i < BAR_COUNT; i++) {
			temp = retrieve(i);
			// int j;
			// int k = j+1;
			for (j = i - 1; (j >= 0) && (retrieve(j) > temp); j--) {
				paint(j, COLOR_ACTIVE);
				paint(i, COLOR_ACTIVE);
				assign(j + 1, retrieve(j));
			}
			assign(j + 1, temp);
			// int f = retrieve(j);
			// assign(j, retrieve(i));
			// assign(i, f);

			delay();
			paint(j, COLOR_FINALIZED);
			paint(i, COLOR_FINALIZED);

		}
		paintAll(COLOR_INITIAL);

	}

	public void bubbleSortAll() {
		int j = BAR_COUNT;
		for (int c = 0; c < (j - 1); c++) {
			for (int d = 0; d < j - c - 1; d++) {

				int e = d + 1;
				if (retrieve(d) > retrieve(e)) {

					paint(d, COLOR_ACTIVE);
					paint(e, COLOR_ACTIVE);

					// int temp = retrieve(d);
					// swap(d,e,temp);
					int temp = retrieve(d);
					assign(d, retrieve(e));
					assign(e, temp);

					delay();
					paint(d, COLOR_FINALIZED);
					paint(e, COLOR_FINALIZED);
					delay();

				}
				// delay();
				// paint(d, COLOR_FINALIZED);
				// paint(e, COLOR_FINALIZED);
				// delay();
			}

			// paint(d, COLOR_FINALIZED);
			// paint(e, COLOR_FINALIZED);
			// delay();

			paintAll(COLOR_INITIAL);
		}
	}

	private void swapHalves() {
		final int half = bars.size() / 2;
		final int offset = bars.size() % 2;
		for (int i = 0; i < half; i++) {
			final int j = i + half + offset;

			paint(i, COLOR_ACTIVE);
			paint(j, COLOR_ACTIVE);

			int temp = retrieve(i);
			assign(i, retrieve(j));
			assign(j, temp);

			delay();

			paint(i, COLOR_FINALIZED);
			paint(j, COLOR_FINALIZED);
		}
		paintAll(COLOR_INITIAL);
	}
}