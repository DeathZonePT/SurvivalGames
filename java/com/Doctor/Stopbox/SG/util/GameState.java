package com.Doctor.Stopbox.SG.util;



public enum GameState {
	WARMUP, IN_LOBBY, IN_GAME, POST_GAME, RESSETING, DISABLED;
	
	private static GameState currentState;


	public static void setState(GameState state){
		GameState.currentState = state;
	}
	
	public static boolean isState(GameState state){
		return GameState.currentState == state;
	}
	
	public static GameState getState(){
		return GameState.currentState;
	}
}
