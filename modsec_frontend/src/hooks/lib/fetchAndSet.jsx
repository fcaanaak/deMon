/**
 * Get the resource from the url specified using fetch and call a setState function on the JSON representation
 * @param url The url to the endpoint to hit
 * @param setState The setState function provided by the useState hook
 * @param errMessage The message to display alongside the error in the event that fetch fails
 */
function fetchAndSet(url, setState, errMessage) {
    fetch(url)
        .then((res) => res.json())
        .then((data) => setState(data))
        .catch((error) => alert(`${errMessage} : ${error}`));
}

export default fetchAndSet;