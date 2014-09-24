GoogleImageSearcher
===================

A cool android app to search images. In the background uses the Google Image Search api.

Time spent: 12 hours spent in total

Completed user stories:

Required:
* User can enter a search query that will display a grid of image results from the Google Image API.
* User can click on "settings" which allows selection of advanced search options to filter results
* User can configure advanced search filters such as:
  - Size (small, medium, large, extra-large)
  - Color filter (black, blue, brown, gray, green, etc...)
  - Type (faces, photo, clip art, line art)
  - Site (espn.com)
* Subsequent searches will have any filters applied to the search results
* User can tap on any image in results to see the image full-screen
* User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

Optional:
* Robust error handling, check if internet is available, handle error cases, network failures
* Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* User can share an image to their friends or email it to themselves
* Replace Filter Settings Activity with a lightweight modal overlay

Todo:
* Improve the user interface and experiment with image assets and/or styling and coloring
* Bonus: Use the StaggeredGridView to display improve the grid of image results
* Bonus: User can zoom or pan images displayed in full-screen detail view

Notes:
Spent quite some time on the spinner and getting the search filters layout correct and infinite scrolling. 

Walkthrough of all user stories:


