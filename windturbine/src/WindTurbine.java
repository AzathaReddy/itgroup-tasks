import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is a Wind Turbine simulation application.
 * It displays a wind turbine that rotates based on the speed value read from a settings file.
 */
public class WindTurbine extends Application {

    // Path to the settings file
    private static final String SETTINGS_FILE = "C:\\Users\\saira\\eclipse-workspace\\windturbine\\src\\settings.txt";

    // Default speed of the wind turbine
    private static final double DEFAULT_SPEED = 5.0;

    // Minimum and maximum speed limits for the wind turbine
    private static final double MIN_SPEED = 1.0;
    private static final double MAX_SPEED = 10.0;

    // Current speed of the wind turbine
    private double turbineSpeed = DEFAULT_SPEED;

    // Current rotation angle of the wind turbine blades
    private double rotationAngle = 0.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400, Color.WHITE);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        Rectangle pillar = new Rectangle(10, 200, Color.BLACK);
        pillar.setStroke(Color.BLACK);
        pillar.setStrokeWidth(2.0);
        stackPane.getChildren().add(pillar);

        Canvas canvas = new Canvas(200, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        stackPane.getChildren().add(canvas);

        root.getChildren().add(stackPane);

        primaryStage.setTitle("Wind Turbine");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdateTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= 5_000_000_000L) { // Check every 5 seconds
                    try {
                        // Read the speed setting from the file
                        double speed = readSpeedSetting();

                        // Update the turbine speed if it's within the valid range
                        if (speed >= MIN_SPEED && speed <= MAX_SPEED) {
                            turbineSpeed = speed;
                        }
                        System.out.println("Turbine speed: " + turbineSpeed);

                    } catch (IOException e) {
                        System.out.println("Error reading settings file: " + e.getMessage());
                    }
                    lastUpdateTime = now;
                }

                // Clear canvas
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Update rotation angle
                rotationAngle += turbineSpeed;

                // Translate and rotate the turbine
                gc.save(); // Save the current transform state
                gc.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
                gc.rotate(rotationAngle);

                // Draw turbine blades
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(-5, -100, 10, 200);

                // Draw turbine head
                gc.setFill(Color.DARKGRAY);
                gc.fillOval(-25, -25, 50, 50);

                // Draw center hub
                gc.setFill(Color.GRAY);
                gc.fillOval(-10, -10, 20, 20);

                gc.restore(); // Restore the previous transform state
            }
        };
        timer.start();
    }

    /**
     * Reads the speed setting from the settings file.
     *
     * @return The speed value read from the file, or the default speed if the file is not found or an error occurs.
     */
    private double readSpeedSetting() throws IOException {
        double speed = DEFAULT_SPEED;

        try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                speed = Double.parseDouble(line);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid speed value in settings file.");
        }
        System.out.println("Read speed from settings file: " + speed);

        return speed;
    }

}
