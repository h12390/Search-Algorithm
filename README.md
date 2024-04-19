# Search Algorithms for Terrain Navigation

This Java application simulates pathfinding on varied terrain using different search algorithms including BFS, UCS, and A* search. The application evaluates paths based on terrain characteristics like elevation changes and muddiness, optimizing for the least costly path.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Input Format](#input-format)
- [Output Format](#output-format)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Multiple Search Algorithms**: Implements Breadth-First Search (BFS), Uniform Cost Search (UCS), and A* Search.
- **Terrain Analysis**: Considers terrain features such as height and muddiness to determine the cost of travel between points.
- **Path Recovery**: Tracks and outputs the optimal path from a specified start point to multiple potential end points.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or later
- Basic understanding of Java programming and environments

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/search-algorithms.git
   ```
2. Navigate to the cloned directory:
   ```sh
   cd search-algorithms
   ```

## Usage

To run the program, you need to compile the Java files and then execute the main class:

1. Compile the Java code:
   ```sh
   javac search.java
   ```
2. Run the compiled program:
   ```sh
   java search
   ```

### Input Format

The program reads input from a file named `input.txt` with the following format:
- First line: Search algorithm to use (`BFS`, `UCS`, `A*`)
- Second line: Grid dimensions as `width height`
- Third line: Start coordinates as `x y`
- Fourth line: Maximum steepness the agent can traverse
- Fifth line: Number of goals
- Subsequent lines: Coordinates of goal points
- Remaining lines: Grid values representing height or muddiness

### Output Format

The program writes to `output.txt`, listing the path from the start to each goal point. If no path is found, it writes `FAIL`.

## Contributing

Contributions are welcome. Please fork the repository and submit pull requests with your improvements.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
