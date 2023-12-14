val sampleData = """
    ....#.#....O...OO..#..#....OO...#.#..OO#.#O.#.O.#.....O....O......O..........O.....O....#..#O..#O...
    O#....O..#O........O.#...OO....#O...O#.#.....O.....O.O....##...O.O..O.#.O......#....O.O.....###....#
    ....#.#O..........O...............O.O..#....#..O#.#.........#..#......#..O..O.OO..O.O#O.OO..O..OO..#
    ...#OO....O.......O#.OO#.O#..O#.O..O#OO..#OO.O.#..OO......O...##.....#..O##O.#O.......OO.#......#...
    .#..#.O..#....#O....#O..OO...#O.#..O..#.....O#....OO......OOO#.#.....#..O.##.OO....#...O.#.O#....#O.
    ..#.#OO#..#.O..O.#..#O.......O...###......#.#.O..#.O.#...O.....O.O#...OO...#..O..#...O....#....#.O#.
    ......OO....OO...#O.......O##..#........#O....O.........#.###..O...O.O........OO#.....O#.#.##......O
    ..#....#.#...O...#..##...#O....##..#..#....O..O...O.#.....O.#.......O.OO....#OOOO.#.....O#.#..OOO...
    .O..##.#O...#...O......OOO.......#O.O..#.#....#.....OO.O...OO#.O.O....#.#.............O##....##O##..
    O#..O........#..#...#.....#..#O.#O....###...#....#..#OOO.....O.O###.O..#O...O...##..O....O..O....O.#
    ...O....#..O.....O..O...O....#.O...#.#....OO..O.##....#.OO..O...##....#..#OOO.#O#...O#O#.....#..OO..
    .....#.##.#..OO..#..#O#O#OO.O......O....#.O..##O....#..#..#...O#O.#.O..O....O.......##.O#...O..#...#
    .....OO.....#.....OO........#.OOO.O.#OO......#.#....#....#.##..O..###.....#....O.O.......OO.OO.#.#..
    #.............OO......OO.OO#.#...#.##.......#.#..O#..#.O.#.....#.O..O...O.O........#O..O..O..O....#.
    O..O.O..OO#O...OO...O...#.O....#...O...O#...#.........O...#.#OO..OO..O.O........#OO.##.O#..O......OO
    ..##.##.O.##O.O...O.OO#...........#O...O..O..#O...........#.#.###.#.#.O.O....##..#.O.OO...#.....O#..
    ....O......OO.O..#.......O..O.#.#O....O..O.#....O..##.O#...#..O#.#.##..O...#O...#.O.....#.O...O#....
    .#.O.O.#....##...#..##..#.#.O.#..O.....#..##..#..O.......#....O......O.O.........OO.#...#.#O...O.O.O
    ....O#..#....OO#..OO.OO.O..#OO#.O.......#OO.#O..O.#..#..#OO...O.....#...O........#..#..O...O..O.O#..
    #.O..........OO#O...........OO....##.#.O.....OOO.#.......#.......O...#..OO....#....#.......O.O....#.
    ..O#......O.OO......O##......O#O....#OO...#.OO....OO#.O#.O.#O..O.....O......O.....#.#O...#OO#..O.#O.
    .#...#OOO...O....O...OO..#OO.#.O.OO.O.O.O.O...#..O.....O.#O#.O.O.....O##...#....O##.#.O....#..O...OO
    ...........#..........O#...#.....O#..O.....#O.O.....O.O..O.#.OO.O...O#......O....O#......OO..O.O..#.
    #..#.OO..#.O.##O.O..#....#O#O#..#...#OO..O....O...O..##....OOO.....O#..O..##O..O.O##.OO..#.........O
    O.O.##......#...#O.##.#.OO..O....O.O.O#..O..####........O......O....O.......O#.....O.O.#.O#.O.#OO#..
    .....OO.#..OO..O..O.#.#....#..OO..OO.....O#......O.#.#........#...........#....#......O....O..OO.#.O
    O##..O.#...OO#..#..##O...O##.#O##.O.........O...OO#..O.#.....#O.#.O.....#...##..#O.#...OO.#O.#.#.#..
    ...#..O..O....OOOOO..#..#OO..O.#...#....#...O........#.#........O.....O..O....OOO.....#.O..##..O.##O
    .O.#O.....#O.O#...#..#.OOO.O#.O....#.O...........#.....#...#...O....O.#...O..#...O#.......O..O......
    ..........O##...O....##.....O..#.......##.....#..##.#O....#..O..##O...OO...O.....#.O..#..###O.##..O#
    ..###O.O.#.#O....O#.##..O.#..##.##O.O.#..O#.#.O#.O.##...O.......#...O.#.......#..##.O...#..O....O.O.
    ...O..O#.##O.....O.O.#O..O#.O##.O.#....O#.#O.O..#......#..###.O#O.##.........O...O...O#....O........
    .O....O.#....#.OO#..#O...#.......#......OOO.#OO.O#.#.O......#O#OO..#O.O..#..#.O.............O#.OO#.#
    #O.O.....#.O.O#...OO.......O...O..O.#..........O......O.O..#O.#.......O..#O#...O...OO.#..#.#...#...O
    .....#....#.....#O..O...O...#.....#OO....#..#O.O.#OO..#.O.O#...#.O.O..#..#..#OO#..#..#..O...#..O#..O
    ....O..OOO.##...#.O#......#O.O.O....#O..#.O..#........O#.O...O........#O#O..#..#.O...O.##.O.O.....O#
    .O..O#.O..#.....###..#......#OO..........O.#.O#..OOO.###.....#..OO.#O...O...OOOO...O.........O......
    O...O.........O......OO...#..O.##..#..#..O.....O..O.........O.O..OO....#..O.#..##OO#.#....#.###.....
    .OO.O.#......#.O.......##..O.O.O#..O.OO...O#..#.......#.........OO#.....#..O..O...O.O....#.O..O#....
    O...#..O.OO..O.#.O...#.......#.O.O.O.....#.....O..OOO#...O.#O.O#..O...O#...........O#..O........O...
    O#O.#O..#.......#..O.#..#....#..O..#..O.#......O.OO.O.....#.O.#....O.#.......O......O#.O...#.#.#.O..
    ..O#O.OO..#......#.#.#....#..#...#..O.#..#O.O.......O.##...O.O.O.#O...O.O.O##O..#..O...O..OO.#..#.#.
    ...O.#.....#O...O....O....O....OO..........O.O#O.O.O###...##O.#O...#O.#.OO...#..#..O##...O......#...
    .##.##..O..O#...#O...OO.#...O..##....O....#.OO...#O.O..#..O.#.....O.O.#.##O..O..O.O.......O...O...#.
    O.OO.O.##.O..##OO.............#.....O....#O..O#...#.##.#.#.........OO..OO..O....OO.#O.O..O..........
    #..O.O....OO..O..#...O.....O.#.O#.O.#.#OO#......O.O..O.O...#.OO.#.O#..#O.#..OO.#..#.##.....#OO...OO.
    ..OO##.#........OOO..O.##....O.O#...OOO......O...O..O.#......#.O...O.#..O.O..O.O..O.O...#O.....OO.#O
    O..#..O.#.O...#OO##......O.O.....#O.#O#O.#..#..#.OO..#.O##......O.#O....#.....#...#...O#...O.O....#.
    O.........O#O#.#.#......#.O##.O.O#......O.#...OO....#...#..#O#.OO#.###.O.#.#..O..#..#OOO#O#.O...#O.#
    .O#.O....O..OO..OO#...#O........O.OO.O#O##.O#O#....#...#O#........#.#O.O#..#..O##..##.........#.O##.
    ...##...O.#O.##....#.....O....#..OO..O...#.O.O......OO#..O#..#####.#O..#.#....O.#...O...........#O..
    .O.#.O..OO.O...#.#....#...O#....#O..#O.#.O.O.O..#..O#.O...O..O.O#O.....O......#O......#O..#.#O.O..OO
    ......#........O.#O.OO.#...O...##.....#......OO...#O#OO..OOO....#......#..##..#.O...OOOO.#....O...#.
    O...#O..OO.....OO.OOOOO#.O....#...#...O..#..#O....##.OO.#OO.OO..#O..#O.O#.OOO.............#OOO....##
    .#.O.O..O.....#..#....#...OO...#...OO.#........O.O....##..........#.#O...OO###OOO..#...#..OOO..O..O#
    O#...OO..O.#........##......#O....#.........#....O#.#....#..O...O#..O.#......OOO.#....O..OO...O##.OO
    ....#O#.O..O.O..#O.O#....O.#.O...OO...O..OOO..O.O.O#.#...#.......O.O..#....O...#O.##.....#.#O#.OOO#.
    .O###...#...OO.O.....O.....#..OOO.##O....OO.O.............O......O..#.O..#....#..#...#..#O..O...#OO.
    ...O...#..........#.O..O.........O.O.....#..#....O#O...#.O.#OO.##O...OO..........#O..#....#O....O...
    ..O..O.#.....O#O..#O##O.#O.O##.#.....O.O.O.#.O.....OO..OOO.O#.OO.#...O..O...#.O.OO#..OO.#..OO####OO.
    O.......O#..O.O.#.#...O.OOO...#O....#O.O.O.O...#......O.##...#........#..#..#.#.O##..O.OO.#O..O.....
    ..O..OO..#....O..OO.#.##....#.......#..#.#..O#..#.#OO#..........#O#O...O.OO...O..#....O...#.......O.
    ....#.#.#O..O........#.O#.O#..#.......#.#...##OOOOO.OO.O#..#....OO..OOO.O......O.........#..OO.O.#.O
    O...O.....#O###..O.OO.O...O...#...#O.....OO...#.....O.O.O..#...O.O..#O..O.O.#...#O.O..#..O....#OO..#
    ...#..#......#.O#....#O#.O....O..O..O..O....O...#..#O..#.....#...O.....OOO..OO.#O#.......#.O...OOOOO
    OOO#.O.O.#O....O..#...O..O#O..O#...#...#..O#..O..O.O#......O...#O..O#.O...#.O.#...O...OO.#....O.....
    O..#O...O#..#...O.#.O......#.#..#.O...##.O..#O...O#.#.O.....O...OO.OOO.O.#..#.O....##.#.#O..OO...#OO
    ...O.O..O.OO.O..O.OOO.O#.#..O.....OO#..OOO.#.#....O.#O.#.O.OO....O..#..O#O###OO#.......##........#..
    #...#..O.###.O.#O......OO#......O.O..#O......OO.O.#....#....O...OO##OO#O...O.........O#OOO..........
    ..O.O..............O.....OOOO#.OO..O#..O.###.O..O.#.O.O.....O.#O.OOO..OO#..OO.#.....#.......O#...O..
    O...#.OOO....OO......O..O......O.....O....O..#O..#.O.............O.#..............#....#O....#O#O...
    .OO.OOO.........OOO#.OO....#...#....OO...O..O.....#...#O..#O.O.#.O.#O......O.O.O#..O.O.#...#O....O#.
    O.....#OO......#.#..OO.O.##O#......#......O..#...#O#..#O..O..#.........O..##.O..##O.#.#.O.#..OOO#..O
    ...O....#..#....OO.O.##...OO#.O##..........##.#..OO.#..O#.#.O..#...O..#..O..#....O..#....O..#.###O..
    .O....O...O#....O#O..O..OO...#O.O.....#...#.......#...##.O..#...#.....O......O..##...##......O.#...O
    O#.O#OO.O.#O.#.#O..#.O..O..#O#O.#O...........O#.O...O...O....O......O.OO......O..O##..O....OO#...##O
    ..O..O.OOO....#O#..O#...#.O....#....#O..O#..##.O.....O.O#O..O.....##...#..#............#....O.O.#.O#
    .....O.#..O..#.OO.....O...O..#.......O....#.##.O#O....O.O...#.OO....##O....O....#OO#O.......O#..#O.O
    #O.O.#......#.O....O....#..O##......O.#.#....#OO.....O.OOO...#.O.....O#.O.O#..O#..#.....#.OO...OO.O.
    ..#O........O.O.O.O.....O..#...O.O.#O.OO....O...#.......##...O.......OO.#O.O.O..O.##...O.O#..##..#O.
    O#O....O.O.O#.###O...#.....O..#....#.#.O.....O..####.O.O..#..OO.#..O....##..#O.##.....O...O.....OO..
    #...OO.#.#.#...O.#O....#O#.............###.O..O.O.O.#...##O..O#...OO#OO.....###.O..#.#...O.....O..O.
    .O.OO#..O..OO#.O#.O..##....O#...O...O....OO.....#.#O.....O......O##......#....#..#......O##.#....#O#
    .#O#O..#....##.OO.............O..##....O......#.........#.......#O..O##....#.....#..#......#O.OOO...
    .O.O#OO..#..........O.#O.OO......##....#..O###O..OO..O..O.##O..#.......#..O..#..#.#......O...#....#.
    .#.O....OO.O....#O..OOO.#....O.....OO..O..O...O...#..O....O......O...........#.O.....#...##..O.O#.OO
    .O.O..O#O.OO..OO#.OO.#O.....O...#.OOO....O#......O.OO..#.#..O.....#..O.O#O.O#..O##O..O....O....O....
    ..OO......#..#O...OO..O.#..#O##....O..#.#.O...O..#O....#.O.....OO.O.OO...OO...O#.O.....OOO...#....#.
    #OO..#.....#OO...O.......O....#...O...O.#O....O....O....#.......O....#..O.....#....OO.##.OO#O.O#....
    #..#O....O#...#.#OO..............#OO#OO#.O....O..OO.O.O#...O...#..#O.#.......O....#O..#..#O.....##O.
    ...#.....#.#..#OOOO..O.O#O..O..#..O..O.............OO...#.....#O.OO..#..#.....OO.....#O....#..O#....
    ...O.#.O.#O.O.O##..O..O.O......O#O##...........O..#..#...........O.O.O...O..#.....OO.O.......#O.....
    #.O#O.....#O......O#.......OO.O.O..#..#..#O....#......#.O..O..#O#.###.#..#..#...O.OO....#.#O#..O...#
    #..O..#.O.O......#...#......#..#......O...O..#....O#.O.O..O...O..#..#O.OOO..O...#.......O..OO.O....#
    ...O..OO#O..#....#....###O...O#.#OO..O...O....O#.#....#.O......#..#...#..#.O......O..O...#.O....#.O.
    OO..#..O##.O..O...O..#O....#....#.O.O....O#....OO.#.OO..O..OO..O#...O....OO..#.OOOO.#..O.O.....#..##
    .OO.O....O...O#O.....##....O..#O##....O.....##....O.#..#O#O.OOOO..O..O.O.......OO..O.#....O.....O..O
    .O.O..O#..#OO..O.O.....OO..OO..##.#..O...........#.O..#.O.#...O...O..OOO..O.#O.#....O.#.OO..O#......
    ..O#..O#.O...##..#.#.###....O#..O.O##.O.#.O....OO.#..O.OO.#....O..O#O.#...##...OO#.OO..O.....#.....O
    ...#OO#.......O.O.##...#O..O#.O#O#..OO..O..O#O..O.#.OO#.....#.....O...O.O.....#O#.OO.O...O.O#..#O..#
""".trimIndent().split("\n")