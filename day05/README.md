# How Part Two works #

In the sample data the seeds have range 79..82 and 55..67

The seed->soil map is:
Range1: 98..99 offset the range by -48
Range2. 50..97 offset the range by +2

So first process seeds 79..82. This is simple as 79..82 is completely inside range1 which is 50..97. So the range of soils for the first seed is 81..84.

But what if range1 was 50..80? In that case there are two possible ranges for the input which would be 79..80 (which match range 1) and 81..2 (which doesn't have a match).  
When a seed range is matched against a single soil range then it could be split up using the following rules:
+ seed is completely before the soil range (so don't change it)
+ seed is completely after the soil range (so don't change it)
+ seed is completly inside the soil range (so don't change it)
+ seed starts before the soil range and finish after the soil range (create three new ranges: the portion of the seed before the soil range, portion of the seed overlapping with the soil and portion of the seed after the soil)
+ seed starts before the range but overlap (create two new ranges: the portion of the seed before the soil and the portion of the seed inside the soil range)
+ seed starts after the range but overlap (create two new ranges: the portion of the seed inside the soil rage and the portion of the seed after the soil range)

After splitting up your seed range using the first soil range you then need to compare the new list of seeds with the next soil range and so on.

After converting a seed range to a list of soil ranges you then convert your list of feritliser ranges to a list of water ranges using the same process.

