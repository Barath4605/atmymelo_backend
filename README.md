============================================== CONTROLLER ============================================== 


ALBUM CONTROLLER
=============================================

# ALBUM CONTROLLER :
1. Album Search `/api/albums/search`
2. Open the Album Page `/api/albums/{mbid}`

# ALBUM PAGE CONTROLLER :
1. RATING `/api/albums/{mbid}/rate`
2. FAVORITES `/api/albums/{mbid}/favorite`
3. QUEUE `/api/albums/{mbid}/queue`

# ALBUM-ARTIST CONTROLLER :
1. Fetch all Albums from the Artist `/api/artist/{id}/albums`

# FAVORITES CONTROLLER :
1. Fetch all the Genres of the albums favorited by the user `/api/favorites/genre`
2. Get all the albums under one genre `/api/favorites/albums-in-genre`

# QUEUE CONTROLLER :

1. Fetch all the Genres of the albums Queued by the user `/api/queue/genres`
2. Get all the albums under one genre `/api/queue/albums`

# REVIEWS CONTROLLER : 

1. User POST Review `/api/albums/{mbid}/review`
2. Fetch all Reviews from the User `/api/albums/{mbid}/all-reviews`
3. Fetch Latest 3 reviews from the User `/api/albums/{mbid}/last-3`
4. Delete Review `/api/albums/delete-review/{reviewId}`

--------------------------------------------------------------------------------------------

PROFILE CONTROLLER
=============================================

# PROFILE CONTROLLER :
1. Open the page from USERNAME `api/profile/{username}`

--------------------------------------------------------------------------------------------

TRACKLIST CONTROLLER
=============================================

# TRACKLIST CONTROLLER :
1. Fetch the tracklist of the Album `api/tracks/{mbid}`

--------------------------------------------------------------------------------------------

AUTH CONTROLLER
=============================================

# USER LOGIN CONTROLLER :
1. Login `api/user/login`

# USER REGISTER CONTROLLER :
1. Register `api/user/register`

--------------------------------------------------------------------------------------------

ARTIST CONTROLLER
=============================================

# ARTIST SEARCH :
1. Search for artists `/api/search`
2. Open the Artist Page `/api/artist/{mbid}`


============================================== SERVICE ==============================================

ALBUM SERVICE
=============================================

## ALBUM-RATING SERVICE
1. getOrCreate(UUID userId, String mbid)
2. rate(RatingRequestDTO rateDto, UUID userId, String mbid)
3. favorite(FavoriteRequestDTO favDto, UUID userId, String mbid)
4. queue(QueueRequestDTO queueDto, UUID userId, String mbid)

## ALBUM SERVICE
1. searchAlbums(String query)
2. getAlbumByMbid(String mbid, UUID userId)
