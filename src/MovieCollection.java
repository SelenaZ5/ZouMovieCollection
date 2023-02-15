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
    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> uniqueCast = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++) {
      String cast = movies.get(i).getCast();
      String nextCast = "";

      while (cast.indexOf("|") != -1) {
        nextCast = cast.substring(0, cast.indexOf("|"));
        cast = cast.substring(cast.indexOf("|") + 1);
        if (uniqueCast.indexOf(nextCast) == -1) {
          uniqueCast.add(nextCast);
        }
      }
      if (uniqueCast.indexOf(cast) == -1) {
        uniqueCast.add(cast);
      }
    }
    System.out.println(uniqueCast.size());

    ArrayList<String> results = new ArrayList<String>();

    for (int i = 0; i < uniqueCast.size(); i++) {

      if (uniqueCast.get(i).toLowerCase().indexOf(searchTerm) != -1) {
        results.add(uniqueCast.get(i));
      }
    }
    System.out.println(results.size());
    if (results.size() > 0) {
      sortStrings(results);

      String member = displayString(results);

      ArrayList<Movie> returns = new ArrayList<Movie>();
      for (int j = 0; j < movies.size(); j++) {
        String castMem = movies.get(j).getCast();

        if (castMem.indexOf(member) != -1) {
          returns.add(movies.get(j));
        }
      }
      sortResults(returns);
      displayReturns(returns, "title");
    } else {
      System.out.println("\nNo result match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void listGenres() {

    ArrayList<String> uniqueGenre = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++) {
      String genre = movies.get(i).getGenres();
      String nextGenre = "";

      while (genre.indexOf("|") != -1) {
        nextGenre = genre.substring(0, genre.indexOf("|"));
        genre = genre.substring(genre.indexOf("|") + 1);
        if (uniqueGenre.indexOf(nextGenre) == -1) {
          uniqueGenre.add(nextGenre);
        }
      }
      if (uniqueGenre.indexOf(genre) == -1) {
        uniqueGenre.add(genre);
      }
    }
    System.out.println(uniqueGenre.size());
    sortStrings(uniqueGenre);
    String genre = displayString(uniqueGenre);

    ArrayList<Movie> returns = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++) {
      if (movies.get(i).getGenres().indexOf(genre) != -1) {
        returns.add(movies.get(i));
      }
    }
    sortResults(returns);
    displayReturns(returns, "title");
  }


  private void sortStrings(ArrayList<String> list) {
    for (int i = 1; i < list.size(); i++) {
      String temp = list.get(i);

      int idx = i;
      while (idx > 0 && temp.compareTo(list.get(idx - 1)) < 0) {
        list.set(idx, list.get(idx - 1));
        idx--;
      }
      list.set(idx, temp);
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

  private void displayReturns(ArrayList<Movie> list, String option) {
    for (int i = 0; i < list.size(); i++) {
      String title = list.get(i).getTitle();

      int choiceNum = i + 1;

      if (option.equals("title")) {
        System.out.println("" + choiceNum + ". " + title);
      } else if (option.equals("revenue")) {
        int revenue = list.get(i).getRevenue();
        System.out.println("" + choiceNum + ". " + title + ": " + revenue);
      } else if (option.equals("rating")) {
        double rating = list.get(i).getUserRating();
        System.out.println("" + choiceNum + ". " + title + ": " + rating);
      } else {
        System.out.println("" + choiceNum + ". " + title);
      }
    }

    if (list.size() > 0) {
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = list.get(choice - 1);

      displayMovieInfo(selectedMovie);
    } else {
      System.out.println("No results match your search");
    }

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRated() {
    for (int i = 1; i < movies.size(); i++) {
      Movie temp = movies.get(i);
      double tempR = temp.getUserRating();
      int idx = i;
      while (idx > 0 && tempR < movies.get(idx - 1).getUserRating()) {
        movies.set(idx, movies.get(idx - 1));
        idx--;
      }
      movies.set(idx, temp);
    }
    ArrayList<Movie> rating = new ArrayList<Movie>();
    for (int i = 1; i <= 50; i++) {
      int idx = movies.size() - i;
      rating.add(movies.get(idx));
    }
    displayReturns(rating, "rating");
  }

  private void listHighestRevenue() {
    for (int i = 1; i < movies.size(); i++) {
      Movie temp = movies.get(i);
      double tempR = temp.getRevenue();
      int idx = i;
      while (idx > 0 && tempR < movies.get(idx - 1).getRevenue()) {
        movies.set(idx, movies.get(idx - 1));
        idx--;
      }
      movies.set(idx, temp);
    }

    ArrayList<Movie> revenue = new ArrayList<Movie>();
    for (int i = 1; i <= 50; i++) {
      int idx = movies.size() - i;
      revenue.add(movies.get(idx));
    }
    displayReturns(revenue, "revenue");
  }
}