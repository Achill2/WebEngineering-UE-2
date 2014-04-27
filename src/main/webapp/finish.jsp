<%@ page import="at.ac.tuwien.big.we14.lab2.api.Winner"%>
<jsp:useBean id="quizData" scope="session"
	class="at.ac.tuwien.big.we14.lab2.api.beans.QuizData" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Business Informatics Group Quiz - Spielende</title>
<link rel="stylesheet" type="text/css" href="style/screen.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/framework.js" type="text/javascript"></script>
</head>
<body id="winnerpage">
	<a class="accessibility" href="#roundwinner">Zur Spielwertung
		springen</a>
	<header role="banner" aria-labelledby="mainheading">
		<h1 id="mainheading">
			<span class="accessibility">Business Informatics Group</span> Quiz
		</h1>
	</header>
	<nav role="navigation" aria-labelledby="navheading">
		<h2 id="navheading" class="accessibility">Navigation</h2>
		<ul>
			<li><a id="logoutlink" title="Klicke hier um dich abzumelden"
				href="#" accesskey="l">Abmelden</a></li>
		</ul>
	</nav>

	<section role="main">
		<!-- winner message -->
		<section id="roundwinner" aria-labelledby="roundwinnerheading">
			<h2 id="roundwinnerheading" class="accessibility">Endstand</h2>
			<p class="roundwinnermessage">
				<%
					String message;
					if (quizData.getWinner() == Winner.PLAYER1) {
						message = quizData.getPlayer1Name() + " gewinnt!";
					} else if (quizData.getWinner() == Winner.PLAYER2) {
						message = quizData.getPlayer1Name() + " gewinnt!";
					} else {
						message = "Unentschieden!";
					}
				%>
				<%=message%>

			</p>
		</section>

		<!-- round info -->
		<section id="roundinfo" aria-labelledby="roundinfoheading">
			<h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
			<div id="player1info" class="playerinfo">
				<span id="player1name" class="playername"><%=quizData.getPlayer1Name()%></span>
				<p id="player1roundcounter" class="playerroundcounter">
					Gewonnene Runden: <span id="player1wonrounds"
						class="playerwonrounds"><%=quizData.getPlayer1().getNumberOfWonRounds()%></span>
				</p>
			</div>
			<div id="player2info" class="playerinfo">
				<span id="player2name" class="playername"><%=quizData.getPlayer2Name()%></span>
				<p id="player2roundcounter" class="playerroundcounter">
					Gewonnene Runden: <span id="player2wonrounds"
						class="playerwonrounds"><%=quizData.getPlayer2().getNumberOfWonRounds()%></span>
				</p>
			</div>
			<form id="startNewGameForm" action="BigQuizServlet" method="get">
				<input id="next" type="submit" name="action" value="Neues Spiel"
					accesskey="s" />
			</form>
		</section>
	</section>

	<!-- footer -->
	<footer role="contentinfo">© 2014 BIG Quiz</footer>
	
	<script type="text/javascript">
            
            // 
            $(document).ready(function() {
                if (supportsLocalStorage()) {
                	// browser supports locale storage -> save date and time
                	var current_date = new Date();
                	var current_date_string = 
                		current_date.getDate() + "/" 
                		+ (current_date.getMonth() + 1) + "/" 
                		+ current_date.getFullYear() + " "
                		+ current_date.getHours() + ":"  
                        + current_date.getMinutes();
                		
                	localStorage.setItem("lastGameDate", current_date_string);	
                } else {
                	// browser does not support locale storage - do nothing 
                }
			});
     </script>
</body>
</html>
