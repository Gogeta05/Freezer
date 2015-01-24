/**
 * Function to go forward in browser to a given URL.
 */
function forward(url) {
	window.location.href=""+url+"";
}

function updateMatching() {
	/** Still to do
	 * Should disable/enable matching for the given user.
	 * maybe call URL with username to forward to and bring
	 * the change to the controller:
	 *
	 * Example
	 * updateMatching("Max") {
		 forward(routes.Application.updateMatching("Max"));
		}
	*/
}

function popup(action, template) {
	/**
	 * Still to do (next step I'll work on)
	 * 
	 * action can be "open" oder "close"
	 * template is the wanted template to open in a popup
	 * open the popup as overlay via Jquery
	 *
	 * maybe I put this function into headInclude, still thinking
	 * of the best way to show the popups.
	 */
}