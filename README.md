<h1 align="center"> Sahtel++ API endpoints</h1>


This contains all of the current API endpoints for Sahtel++ API

## Getting Started

This contains information about all API endpoints of [Sahtel++ API](https://github.com/Swackles/sahtelPlusPlsus-API) project

### Schedule /tunniplaan
This contains the schedule of classes

#### GET

Returns the basic schedule as an array containing objects like this:
```json
{
	"date": "The date class is happening (dateTime)", 
	"course": "The course number (int)", 
	"group": "The group that has this class (string)",
	"room": "The room class is happening in (string)",
	"amount": "How many academic hours (int)",
	"description": "Description given by sahtel (string)",
	"code": "The code for the class (string)",
	"teacher": "The teacher for this class (string)",
	"classStart": "The time this class starts (DateTime)",
	"classEnd": "The time this class ends (DateTime)",
	"name": "name of the class (string)",
	"homeworks": "array of homeworks (string[], null)",
}
```

#### POST
You can post query filters which will base the search on it. The query filters should be a JSON string like this:
```JSON
**{
	"DateStart": "The starting date of class (DateTime)",
	"DateEnd": "The ending date of class (DateTime)",
	"Checked": "1 - search by Date, 0 - don't search (int)",
	"Classes": "array of class IDs (string[])",
	"Subjects": "Array of subject IDs (string[])",
	"Teachers": "Array of Teachers IDs (string[])",
	"Rooms": "Array of Rooms string[])"
}**
```
This request will return same data structure as `GET /tunniplaan`

<br>

### GET /subject
This will get the list of possible subjects. It will return a JSON array containing objects like this:
```JSON
{
	"Id": "(int)",
	"Name": "(string)",
	"Code": "(string)"
]}
```


### GET /teacher
This will get the list of all the teachers. It will return a JSON array containing objects like this:
```JSON
{
	"Id": "(int)",
	"Name": "(string)"
}
```

<br>

### GET /room
Get all of the possible rooms classes are in. Will return a json array containing objects like this
```JSON
{
	"Id": "(int)",
	"Name": "(string)"
}
```

<br>

### GET /class
Get all of the possible classes currently in the schedule. Will return a json array containing objects like this
```JSON
{
	"Id": "(int)",
	"Name": "(string)"
}
```
