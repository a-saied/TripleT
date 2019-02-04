enum Difficulty {

	EASY, MEDIUM, HARD;
}


public class Player {

	public Difficulty diff;
	public SocketAddress socket;
	public int port;

	Player(SocketAddress s, int p) {
		this.socket = s;
		this.port = p;
	}

	public void setDifficulty(Difficulty d) {
		this.diff = d;
	}

	@Override
	public String toString() {
		return "Difficulty " + diff +
		", Address " + socket +
		", Port " + port;
	}

}