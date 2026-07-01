import { useEffect } from "react";
import fetchAndSet from "./lib/fetchAndSet.jsx";

/**
 * A hook to get a representation of a resource from the endpoint specified by the URL
 * @param url the url of the endpoint to hit
 * @param setState The setState function provided by the useState hook
 * @param errMessage The message to display alongside the error in the event that fetch fails
 */
function useFetch (url, setState, errMessage) {

    useEffect(() => {
        fetchAndSet(url, setState, errMessage);
    }, []);

}

export default useFetch;