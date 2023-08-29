db = db.getSiblingDB('library');
db.bookStored.updateMany({}, { $rename :{"bookShelf":"bookshelf"}}, false, true)