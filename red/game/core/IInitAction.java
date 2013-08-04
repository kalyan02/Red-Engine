package red.game.core;

public interface IInitAction {
	boolean initialized = false;
	void initialize();
	void destroy();
}
