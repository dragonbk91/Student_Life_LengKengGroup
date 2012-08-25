package lengkeng.group.Grid;

/**
 * @author VietAnh
 * @version 1.0
 * @created 23-Thg7-2012 1:07:28 CH
 */
public class Grid {
	public final static int WIDTH = 720;
	public final static int HEIGHT = 480;
	public final static int TOP = 53; // Le tren
	public final static int BOTTOM = 27; // Le duoi
	public final static int RIGHT = 0; // Le trai
	public final static int LEFT = 0; // Le phai
	
	public final static int RANGE = 80; // do rong 1 o
	
	public final static int NUM_ROW = 5; // so hang
	public final static int NUM_COLUMN = 9; // so cot
	
	public static boolean[][] Block = new boolean[NUM_ROW][NUM_COLUMN];// Vat can 
	public static boolean[][] Item = new boolean[NUM_ROW][NUM_COLUMN];// Do vat	
	public final static int[] COLUMN={ LEFT, 80 + LEFT, 160 + LEFT, 240 + LEFT, 320 + LEFT, 400 + LEFT, 480 + LEFT, 560 + LEFT, 640 + LEFT  }; // Hang so cot
	public final static int[] ROW={ TOP, 80 + TOP, 160 + TOP, 240 + TOP, 320+ TOP }; // Hang so hang
	public static int[][] CountFootsteps = new int[NUM_ROW][NUM_COLUMN]; // dem so buoc chan, ko cho qua 3 buoc
	// Kiem tra Item
//	public static boolean checkItem(final float x, final float y){
//		return Item[getCol(x)][getRow(y)];
//	}
	public static boolean checkItem(final int x, final int y){
		return Item[x][y];
	}
	// set vao Item
	public static void setItem(final int x, final int y, final boolean value){
		Item[x][y] = value;
	}
	
	// Kiem tra Block
//	public static boolean checkBlock(final float x, final float y){
//		return Block[getCol(x)][getRow(y)];
//	}
	public static boolean checkBlock(final int x, final int y){
		if( (x<0) || (x>NUM_ROW) || (y<0) || (y>NUM_COLUMN))
			return false;
		return Block[x][y];
	}
	// set vao Block
	public static void setBlock(final int x, final int y, final boolean value){
		Block[x][y] = value;
	}
	
	/**
	 * 
	 * @param x
	 * @return Column
	 */
	public static int getCol(final float x){
		return (int) ((x - LEFT)  / RANGE);
	}	
	
	/**
	 * 
	 * @param y
	 * @return Row
	 */
	public static int getRow( final float y){
		return (int) ((y - TOP) / RANGE);
	}
	
	/**
	 * 
	 * @param col
	 * @return coordinate of col
	 */
	public static int getPosX(final int col){
		return COLUMN[col];
	}
	
	/**
	 * 
	 * @param row
	 * @return coordinate of row
	 */
	public static int getPosY(final int row){
		return ROW[row];
	}
	
	/**
	 * 
	 * @param _x
	 * @return standardize X of _x
	 */
	public static int standardizeX(final float _x){
		int x = getCol(_x);
		return getPosX(x);
	}
	
	/**
	 * 
	 * @param _y
	 * @return standardize Y of _y
	 */
	public static int standardizeY(final float _y){
		int y = getRow(_y);
		return getPosY(y);
	}	
	
	// thay doi x, y de cho phu hop voi khung hinh -----> tang do nhay
	public static int resetX(final int pX){
		if(pX <= LEFT) return LEFT + 20;
		else
			if(pX >= 720 - RIGHT ) return 720 - RIGHT - 20;
			else return pX;
	}
	public static int resetY(final int pY){
		if(pY <= TOP) return TOP + 20;
		else
			if(pY >= 480 - BOTTOM ) return 480 - BOTTOM - 20;
			else return pY;
	}
	
	public static boolean checkInScene(final float x, final float y){		
		if ( ( x >LEFT )&&( x < 720 - RIGHT) && (y > TOP) && (y < 480 -BOTTOM) )
			return true;
		else return false;
	}
	
	/**
	 * 
	 * @param mStudentX
	 * @param mStudentY
	 * @param X
	 * @param Y
	 * @return true if (mStudentX, mStudentY)'s near (X,Y)
	 */
	public static boolean checkNear(final float mStudentX, final float mStudentY, final float X, final float Y){
		
		if (!checkInScene(X, Y)) return false;
		else{
			int mCol = getCol(mStudentX);
			int mRow = getRow(mStudentY);
			int pCol = getCol(X);
			int pRow = getRow(Y);			
			if (Grid.checkBlock(pRow, pCol)) return false;
			if(((mCol == pCol) && ((mRow == pRow-1 ) || (mRow == pRow+1 ) )) || 
					(((mCol == pCol-1) 	||(mCol == pCol+1)) && (mRow == pRow )))
				return true;
			else
				return false;
		}		
	}
	
	/**
	 * 
	 * @param mStudentX
	 * @param mStudentY
	 * @param X
	 * @param Y
	 * @return true if (mStudnetX, mStudentY) align (X,Y)
	 */
	public static boolean checkAlignment(final float mStudentX, final float mStudentY, final float X, final float Y){
			int mCol = getCol(mStudentX);
			int mRow = getRow(mStudentY);
			int pCol = getCol(X);
			int pRow = getRow(Y);
			
			if(((mCol != pCol) && (mRow != pRow )) ||((mRow == pRow) && (mCol == pCol ))) 
				return false;
			else 
			// Co vat can	
				if (mCol == pCol) { // cung cot
					if (mRow > pRow) // o duoi student 
						for (int i = pRow; i < mRow ; i++ ){
							if (Block[i][mCol]) return false; 
					}
					else // o tren student
						for (int i = mRow+1 ; i <= pRow; i++){
							if (Block[i][mCol]) return false;
					}
				}
				else { // cung hang
					if (mCol > pCol) // ben trai student 
						for (int i = pCol; i < mCol; i++ ){
							if (Block[mRow][i]) return false;
					}
					else  // ben phai student
						for (int i= mCol+1; i <= pCol; i++){
							if (Block[mRow][i]) return false;
					}
				}
			return true;
	}

	/**
	 * 
	 * @param mStudentX
	 * @param mStudentY
	 * @param X
	 * @param Y
	 * @return direction of footsteps
	 */
	public static int getDirection(final float mStudentX, final float mStudentY, final float X, final float Y){
		int mCol = getCol(mStudentX);
		int mRow = getRow(mStudentY);
		int pCol = getCol(X);
		int pRow = getRow(Y);
		if( mRow == pRow ){ // Cung 1 hang
			if(mCol < pCol )// Phai: direction = 1
				return 1;			
			else if(mCol > pCol) // Trai direction = 3
				return 3;
		}
		else
			if(mCol == pCol){ // Cung cot
				if(mRow < pRow)// Len: direction =1
					return 2;
				else if(mRow > pRow)// Xuong: direction =0
					return 0;
			}
		return -1;
	}
	
	/**
	 * reset all value of Grid
	 */
	public static void reset(){
		for(int i = 0; i< NUM_ROW; i++)
			for(int j = 0; j< NUM_COLUMN; j++){
				Item[i][j] = false;
				Block[i][j] = false;
				CountFootsteps[i][j] = 0;
			}
	}
}
