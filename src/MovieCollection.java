import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)

        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double rating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        // create a Movie object with the row data:
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview,
                runtime, genres, rating, year, revenue);

        // add the Movie to movies:
        movies.add(nextMovie);

      }
      bufferedReader.close();
    } catch (IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++) {
      String movieKeyword = movies.get(i).getKeywords();
      movieKeyword = movieKeyword.toLowerCase();

      if (movieKeyword.indexOf(searchTerm) != -1) {
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      sortResults(results);
      for (int i = 0; i < results.size(); i++) {
        String keyword = results.get(i).getTitle();

        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + keyword);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchCast() {
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();

    ArrayList<String> uniqueCast = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++) {
      String genre = movies.get(i).getGenres();
      String nextGenre = "";

      while (genre.indexOf("|") != -1) {
        nextGenre = genre.substring(0, genre.indexOf("|"));
        genre = genre.substring(genre.indexOf("|") + 1);

        //only add genre to genreSplit list if it isn't already in there!
        if (uniqueCast.indexOf(nextGenre) == -1) {
          uniqueCast.add(nextGenre);
        }
      }
    }

    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> results = new ArrayList<String>();

    for (int i = 0; i < uniqueCast.size(); i++) {
      String castMember = uniqueCast.get(i).toLowerCase();

      if (castMember.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(uniqueCast.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortStrings(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String cast = results.get(i);
        String member = displayString(results);

        ArrayList<Movie> returns = new ArrayList<Movie>();
        for (int j = 0; j < movies.size(); j++)
        {
          String castMem = movies.get(j).getCast();

          if (castMem.indexOf(member) != -1)
          {
            //add the Movie objest to the results list
            returns.add(movies.get(j));
          }
        }
        sortResults(returns);
        displayReturns(returns, "title");
      }
    } else {
      System.out.println("\nNo result match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void listGenres() {
    System.out.println("1.Action\n2.Adventure\n3.Animation\n4.Comedy\n5.Crime\n6.Documentary\n7.Drama\n8.Family\n9.Fantasy\n10.Foreign\n11.History" +
            "\n12.Horror\n13.Music\n14.Mystery\n15.Romance\n16.Science Fiction\n17.TV Movie\n18.Thriller\n19.War\n20.Western");
    System.out.println("Which would you like to see all movies for?");
    System.out.println("Enter number: ");
    int searchTerm = scanner.nextInt();
    scanner.nextLine();

    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieGenre = movies.get(i).getGenres();
      movieGenre = movieGenre.toLowerCase();

      if (movieGenre.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String genres = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + genres);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortStrings(ArrayList<String> list) {
    for (int j = 1; j < list.size(); j++) {
      String temp = list.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(list.get(possibleIndex - 1)) < 0) {
        list.set(possibleIndex, list.get(possibleIndex - 1));
        possibleIndex--;
      }
      list.set(possibleIndex, temp);
    }
  }

  private String displayString(ArrayList<String> list) {

    for (int i = 0; i < list.size(); i++) {
      String title = list.get(i);
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + title);
    }
    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selected = list.get(choice - 1);

    return selected;
  }

  private void displayReturns(ArrayList<Movie> list, String displayOption)
  {
    // now, display them all to the user
    for (int i = 0; i < list.size(); i++)
    {
      String title = list.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      if (displayOption.equals("title"))
      {
        System.out.println("" + choiceNum + ". " + title);
      }
      else if (displayOption.equals("revenue"))
      {
        int revenue = list.get(i).getRevenue();
        System.out.println("" + choiceNum + ". " + title + ": " + revenue);
      }
      else if (displayOption.equals("rating"))
      {
        double rating = list.get(i).getUserRating();
        System.out.println("" + choiceNum + ". " + title + ": " + rating);
      }
      // default display option is title only
      else
      {
        System.out.println("" + choiceNum + ". " + title);
      }
    }

    if (list.size() > 0)
    {
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = list.get(choice - 1);

      displayMovieInfo(selectedMovie);
    }
    else
    {
      System.out.println("No results match your search");
    }

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRated() {
    /* TASK 6: IMPLEMENT ME */
  }
  
  private void listHighestRevenue() {
    /* TASK 6: IMPLEMENT ME */
  }
}