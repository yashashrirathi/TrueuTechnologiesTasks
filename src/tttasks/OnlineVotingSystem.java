package tttasks;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Candidate {
    private String name;

    public Candidate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Ballot {
    private String title;
    private List<Candidate> candidates;

    public Ballot(String title) {
        this.title = title;
        candidates = new ArrayList<>();
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public String getTitle() {
        return title;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }
}

class Election {
    private String title;
    private List<Ballot> ballots;

    public Election(String title) {
        this.title = title;
        ballots = new ArrayList<>();
    }

    public void addBallot(Ballot ballot) {
        ballots.add(ballot);
    }

    public String getTitle() {
        return title;
    }

    public List<Ballot> getBallots() {
        return ballots;
    }
}

public class OnlineVotingSystem {
    private Map<String, User> users;
    private List<Election> elections;

    public OnlineVotingSystem() {
        users = new HashMap<>();
        elections = new ArrayList<>();
    }

    public void registerUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public void createElection(String title) {
        elections.add(new Election(title));
    }

    public void createBallot(String electionTitle, String ballotTitle) {
        Election election = findElection(electionTitle);
        if (election != null) {
            election.addBallot(new Ballot(ballotTitle));
        }
    }

    public void addCandidateToBallot(String electionTitle, String ballotTitle, String candidateName) {
        Election election = findElection(electionTitle);
        if (election != null) {
            Ballot ballot = findBallot(election, ballotTitle);
            if (ballot != null) {
                ballot.addCandidate(new Candidate(candidateName));
            }
        }
    }

    public void castVote(String electionTitle, String ballotTitle, String candidateName, String username) {
        if (!authenticateUser(username, getPasswordFromUserInput())) {
            System.out.println("Authentication failed. Please try again.");
            return;
        }

        Election election = findElection(electionTitle);
        if (election == null) {
            System.out.println("Election not found.");
            return;
        }

        Ballot ballot = findBallot(election, ballotTitle);
        if (ballot == null) {
            System.out.println("Ballot not found.");
            return;
        }

        Candidate candidate = findCandidate(ballot, candidateName);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

        System.out.println("Vote cast successfully for " + candidateName + " in the " + electionTitle + " election by user: " + username);
    }

    private Election findElection(String title) {
        for (Election election : elections) {
            if (election.getTitle().equals(title)) {
                return election;
            }
        }
        return null;
    }

    private Ballot findBallot(Election election, String title) {
        for (Ballot ballot : election.getBallots()) {
            if (ballot.getTitle().equals(title)) {
                return ballot;
            }
        }
        return null;
    }

    private Candidate findCandidate(Ballot ballot, String name) {
        for (Candidate candidate : ballot.getCandidates()) {
            if (candidate.getName().equals(name)) {
                return candidate;
            }
        }
        return null;
    }

    private String getPasswordFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your password: ");
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        OnlineVotingSystem votingSystem = new OnlineVotingSystem();

        // Register users
        votingSystem.registerUser("user1", "password1");
        votingSystem.registerUser("user2", "password2");

        System.out.println("Users registered successfully.");

        // Create an election and add ballots and candidates
        votingSystem.createElection("2023 General Election");
        votingSystem.createBallot("2023 General Election", "President");
        votingSystem.addCandidateToBallot("2023 General Election", "President", "Candidate A");
        votingSystem.addCandidateToBallot("2023 General Election", "President", "Candidate B");

        System.out.println("Election and ballots created successfully.");

        // Cast votes
        votingSystem.castVote("2023 General Election", "President", "Candidate A", "user1");
        votingSystem.castVote("2023 General Election", "President", "Candidate B", "user2");

        System.out.println("Voting process completed.");
    }
}
