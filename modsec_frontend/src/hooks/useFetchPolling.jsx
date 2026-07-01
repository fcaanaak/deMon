import fetchAndSet from "./lib/fetchAndSet.jsx";
import {useEffect} from "react";

/**
 * Repeatedly get a representation of a resource with a delay in milliseconds
 * @param url The url to the endpoint to hit
 * @param setState The setState function provided by the useState hook
 * @param state The state variable provided by the useState hook
 * @param errMessage The message to display alongside the error in the event that fetch fails
 * @param delayMillis The delay between each GET request in milliseconds
 */
function useFetchPolling(url, setState, state, errMessage, delayMillis) {

    useEffect(() => {
        setTimeout(() => fetchAndSet(url, setState, errMessage), delayMillis);
    }, [state])

}

export default useFetchPolling;