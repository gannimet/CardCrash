# CardCrash

CardCrash is a poker evaluation library written in Java.

## Dependencies

Google Guava (https://github.com/google/guava) needs to be added to the class path.

## Creating cards

```java
Card c1 = Card.getCard(Suit.CLUBS, Rank.FIVE);  // five of clubs
Card c2 = Card.fromShortcut("Th");              // ten of hearts
Card c3 = Card.randomCard();
```

## Evaluating hands

```java
Hand h1 = Hand.fromShortcuts("6h", "8h", "Jc", "Js", "Qh", "2d", "Ac");
h1.setId("Player 1");
Hand h2 = Hand.fromShortcuts("7c", "Ts", "Jc", "Js", "Qh", "2d", "Ac");
h2.setId("Player 2");

HandResult result1 = h1.evaluate();
HandResult result2 = h2.evaluate();

HandResultList results = HandResultList.fromResults(result1, result2);

System.out.println(results.getNumberOfPlaces());

int player1Rank = results.getPlaceForResultId("Player 1");
System.out.println("Player 1 rank: " + player1Rank);
System.out.println("Player 1 result: " + results.getResultsRankedAt(player1Rank));

int player2Rank = results.getPlaceForResultId("Player 2");
System.out.println("Player 2 rank: " + player2Rank);
System.out.println("Player 2 result: " + results.getResultsRankedAt(player2Rank));
```
