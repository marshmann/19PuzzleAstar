public class State {
	int[][] board;
	public State(int board[][]) {
		this.board = board;
	}
	public boolean equals(Object obj) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j<6;j++) {
				if(((State)obj).board[i][j] != this.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	public int hashCode() {
		int hash = 0;
	    for (int i = 0; i < 6; i++)
	        for (int j = 0; j < 6; j++)
	            hash = hash * 31 + board[i][j];
	    return hash;
	}
}
