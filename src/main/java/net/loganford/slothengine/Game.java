package net.loganford.slothengine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.config.ConfigurationLoader;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.graphics.Image;
import net.loganford.slothengine.resources.ResourceManager;
import net.loganford.slothengine.resources.loading.ImageLoader;
import net.loganford.slothengine.resources.loading.ResourceLoader;
import net.loganford.slothengine.state.GameState;
import net.loganford.slothengine.state.loading.BasicLoadingScreen;
import net.loganford.slothengine.state.loading.LoadingScreen;
import net.loganford.slothengine.state.transition.InstantTransition;
import net.loganford.slothengine.state.transition.Transition;
import net.loganford.slothengine.utils.file.DataSource;
import net.loganford.slothengine.utils.file.FileDataSource;
import net.loganford.slothengine.utils.file.FileResourceMapper;
import net.loganford.slothengine.utils.file.ResourceMapper;
import net.loganford.slothengine.utils.performance.FramerateMonitor;
import net.loganford.slothengine.utils.performance.PerformanceTracker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Log4j2
public abstract class Game {

    public static final long NANOSECONDS_IN_SECOND = 1000000000;
    public static final long NANOSECONDS_IN_MILLISECOND = 1000000;
    public static final long MILLISECONDS_IN_SECOND = 1000;
    public static final long SLEEP_BUFFER_MS = 2;

    @Getter(AccessLevel.PROTECTED) private boolean running = true;

    @Getter
    private int maxFps;
    @Getter private int minFps;
    protected long lastFrameTime;
    @Getter protected long maxFrameTimeNs = NANOSECONDS_IN_SECOND / 144L;
    @Getter protected long minFrameTimeNs = NANOSECONDS_IN_SECOND / 60L;

    //Default resource mapper to use when converting resource paths in the config.json into files
    @Getter @Setter
    private ResourceMapper resourceMapper = new FileResourceMapper(new File(""));
    @Getter @Setter private DataSource configSource = new FileDataSource(new File("game.json"));

    @Getter private ConfigurationLoader configurationLoader;
    @Getter @Setter private Graphics graphics;
    @Getter @Setter private Input input;

    @Getter private GameState gameState;
    private GameState nextGameState;
    /**The transition which will be used between states*/
    @Getter @Setter private Transition transition = new InstantTransition();
    /**The loading screen which will be used between states and at the beginning of the game*/
    @Getter @Setter private LoadingScreen loadingScreen = new BasicLoadingScreen();


    //Resource Managers
    @Getter private ResourceManager<Image> imageManager = new ResourceManager<>();

    //Measure fps and performance of engine
    private FramerateMonitor framerateMonitor;
    private PerformanceTracker renderTimeTracker, updateTimeTracker, idleTimeTracker;
    @Getter private HashSet<String> loadedResourceTags = new HashSet<>();

    public Game(GameState gameState) {
        configurationLoader = new ConfigurationLoader();
        framerateMonitor = new FramerateMonitor();
        renderTimeTracker = framerateMonitor.getPerformanceTracker("render time");
        updateTimeTracker = framerateMonitor.getPerformanceTracker("update time");
        idleTimeTracker = framerateMonitor.getPerformanceTracker("idle time");

        setFps(60,144);

        this.gameState = loadingScreen;
        loadedResourceTags.add(null); //Initially load default resources
        loadingScreen.beginLoadingScreen(gameState);
    }

    public void initialize() {
        //Load configuration
        log.info("Loading configuration");
        configurationLoader.load(resourceMapper, configSource);

        //Set up graphics
        graphics.initialize();
        graphics.setTitle("Sloth Engine");
        input.initialize();

        //Set up game state
        log.info("Setting up game states");
        gameState.beginState(this);
        gameState.postBeginState(this);

        //Set up timers
        lastFrameTime = System.nanoTime();
        framerateMonitor.start();
        idleTimeTracker.start();
    }

    protected void stepAndRender(float delta) {
        getInput().processInput();

        //Step
        idleTimeTracker.end();
        updateTimeTracker.start();
        gameState.stepState(this, delta);
        updateTimeTracker.end();

        //Render
        renderTimeTracker.start();
        gameState.renderState(this, graphics);
        renderTimeTracker.end();
        idleTimeTracker.start();

        handleTransitions();

        //Performance Tracking
        framerateMonitor.update();
    }

    public void endGame() {
        log.info("Ending game");
        running = false;
    }

    public void setFps(int min, int max) {
        this.minFps = min;
        this.maxFps = max;

        maxFrameTimeNs = NANOSECONDS_IN_SECOND / (long)max;
        minFrameTimeNs = NANOSECONDS_IN_SECOND / (long)min;
    }

    public void setState(GameState requestedNextState) {
        if (nextGameState == null) {
            if (!(gameState instanceof Transition)) {

                boolean loadingRequired = !(new HashSet<>(requestedNextState.getRequiredTags()).equals(new HashSet<>(getLoadedResourceTags())));
                if (loadingRequired) {
                    getLoadedResourceTags().clear();
                    getLoadedResourceTags().addAll(requestedNextState.getRequiredTags());
                    loadingScreen.beginLoadingScreen(requestedNextState);
                    requestedNextState = loadingScreen;
                }

                transition.beginTransition(this, gameState, requestedNextState);
                nextGameState = transition;
            } else {
                nextGameState = requestedNextState;
            }
        }
    }

    /**
     * Handles the transitions between states.
     */
    protected void handleTransitions() {
        while(nextGameState != null) {
            /*We must handle beginning and ending the state for transitions here. We don't need to call this for regular
            * states or loading screens since the transitions that transition to/away from those states call their begin
            * and end state methods.*/
            if(gameState instanceof Transition) {
                transition.endState(this);
            }
            gameState = nextGameState;
            nextGameState = null;
            if(gameState instanceof Transition) {
                gameState.beginState(this);
                gameState.postBeginState(this);
            }
        }
    }

    public List<ResourceLoader> getResourceLoaders() {
        List<ResourceLoader> resourceLoaders = new ArrayList<>();
        resourceLoaders.add(new ImageLoader(this));
        return resourceLoaders;
    }

    public abstract void run();
}
