
<%@ page import="java.util.List"%>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.Choice"%>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.AnswerStatus"%>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.impl.Answer"%>
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
<title>Business Informatics Group Quiz - Zwischenstand</title>
<link rel="stylesheet" type="text/css" href="style/screen.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/framework.js" type="text/javascript"></script>
</head>
<body id="winnerpage">
	<a class="accessibility" href="#roundwinner">Zur Rundenwertung
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
			<h2 id="roundwinnerheading" class="accessibility">Rundenzwischenstand</h2>
			<p class="roundwinnermessage">
				<%
					Winner winner = quizData.getCurrentRound().getWinner();
					String message;
					int roundNumber = quizData.getNumberOfCurrentRound();
					if (winner == Winner.PLAYER1) {
						message = quizData.getPlayer1Name() + " gewinnt Runde "
								+ roundNumber + "!";
					} else if (winner == Winner.PLAYER2) {
						message = quizData.getPlayer2Name() + " gewinnt Runde "
								+ roundNumber + "!";
					} else {
						message = "Runde " + roundNumber + " ist unentschieden!";
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
				<ul class="playerroundsummary">
					<%
						List<Answer> player1Answers = quizData.getCurrentRound()
								.getAnswersPlayer1();
					%>
					<%
						for (int i = 0; i < player1Answers.size(); i++) {
							AnswerStatus answerStatus = player1Answers.get(i).getStatus();
					%>
					<li><span class="accessibility">Frage <%=i + 1%>:
					</span><span id=<%="player1answer" + (i + 1)%>
						class=<%=answerStatus.getCode()%>><%=answerStatus.getName()%></span></li>
					<%
						}
					%>
				</ul>
				<p id="player1roundcounter" class="playerroundcounter">
					Gewonnene Runden: <span id="player1wonrounds"
						class="playerwonrounds"><%=quizData.getPlayer1().getNumberOfWonRounds()%></span>
				</p>
			</div>
			<div id="player2info" class="playerinfo">
				<span id="player2name" class="playername"><%=quizData.getPlayer2Name()%></span>
				<ul class="playerroundsummary">
					<%
						List<Answer> player2Answers = quizData.getCurrentRound()
								.getAnswersPlayer2();
					%>
					<%
						for (int i = 0; i < player2Answers.size(); i++) {
							AnswerStatus answerStatus = player2Answers.get(i).getStatus();
					%>
					<li><span class="accessibility">Frage <%=i + 1%>:
					</span><span id=<%="player2answer" + (i + 1)%>
						class=<%=answerStatus.getCode()%>><%=answerStatus.getName()%></span></li>
					<%
						}
					%>
				</ul>
				<p id="player2roundcounter" class="playerroundcounter">
					Gewonnene Runden: <span id="player2wonrounds"
						class="playerwonrounds"><%=quizData.getPlayer2().getNumberOfWonRounds()%></span>
				</p>
			</div>
			<form id="roundCompleteform" action="BigQuizServlet" method="get">
				<input id="next" type="submit" name="action" value="Weiter"
					accesskey="s" />
			</form>
		</section>
	</section>

	<!-- footer -->
	<footer role="contentinfo">© 2014 BIG Quiz</footer>
</body>
</html>
