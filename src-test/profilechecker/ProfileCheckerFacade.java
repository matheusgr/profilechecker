package profilechecker;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.StereotypeApplication;
import profilechecker.model.ValidationException;

/**
 * EasyAccept facade to use the XMIParser.
 * 
 * @author Matheus
 */
public class ProfileCheckerFacade {

	/** Map of parsed profiles. */
	private Map<String, Profile> profiles;
	private Map<String, Package> packages;
	private Set<StereotypeApplication> applications;
	private Model model;
	private ProfileCheckerController controller;

	/**
	 * Creates the facade.
	 */
	public ProfileCheckerFacade() {
		reset();
	}

	/**
	 * Parse a XMI file and save the profiles in this object.
	 * 
	 * @param file
	 *            File to be parsed.
	 * @throws Exception
	 *             If something fails.
	 */
	public void parse(String file) throws Exception {
		controller = new ProfileCheckerController();
		model = new Model();
		controller.parser(model, new File(file));
		profiles = model.getProfiles();
		packages = model.getPackages();
		applications = model.getApplications();
	}

	/**
	 * Reset this facade to its original state.
	 */
	public void reset() {
		profiles = null;
	}

	/**
	 * Gets the number of parsed profiles.
	 * 
	 * @return Number of parsed profiles.
	 */
	public int getNumberOfProfiles() {
		return profiles.size();
	}

	/**
	 * Gets a property from a profile.
	 * 
	 * @param profile
	 *            Profile ID.
	 * @param property
	 *            Profile property.
	 * @return The profile property value.
	 * @throws Exception
	 *             If something fails.
	 */
	public String getProfileProperty(String profile, String property)
			throws Exception {
		return BeanUtils.getProperty(profiles.get(profile), property);
	}
	
	/**
	 * Gets the number of stereotypes of a profile.
	 * 
	 * @param profile
	 *            Profile ID.
	 * @return Number of stereotypes of a profile.
	 */
	public int getNumberOfStereotypes(String profile) {
		return profiles.get(profile).getStereotypes().size();
	}

	/**
	 * Gets a stereotype property.
	 * 
	 * @param profile
	 *            Stereotype's profile ID.
	 * @param stereotype
	 *            Stereotype ID.
	 * @param property
	 *            Stereotype property.
	 * @return Stereotype property value.
	 * @throws Exception
	 *             If something fails.
	 */
	public String getStereotypeProperty(String profile, String stereotype,
			String property) throws Exception {
		return BeanUtils.getProperty(profiles.get(profile).getStereotypes()
				.get(stereotype), property);
	}

	/**
	 * Verify it a type is associated with a stereotype.
	 * 
	 * @param profile
	 *            Stereotype's profile ID.
	 * @param stereotype
	 *            Stereotype ID.
	 * @param type
	 *            UML type name.
	 * @return True if it is associated, false otherwise.
	 */
	public boolean isStereotypeType(String profile, String stereotype,
			String type) {
		return profiles.get(profile).getStereotypes().get(stereotype)
				.getTypes().contains(type);
	}

	/**
	 * Gets the number of associated types that a stereotype contains.
	 * 
	 * @param profile
	 *            Stereotype's profile ID.
	 * @param stereotype
	 *            Stereotype ID.
	 * @return Number of associated types of a stereotype.
	 */
	public int getStereotypeTypeSize(String profile, String stereotype) {
		return profiles.get(profile).getStereotypes().get(stereotype)
				.getTypes().size();
	}

	/**
	 * Gets the number of parsed packages.
	 *  
	 * @return The number of parsed packages.
	 */
	public int getNumberOfPackages() {
		return packages.size();
	}
	
	/**
	 * Gets a package property.
	 * @param packageid Package ID.
	 * @param property Package property to be read.
	 * @return Property value.
	 * @throws Exception If something fails.
	 */
	public String getPackageProperty(String packageid, String property) throws Exception {
		return BeanUtils.getProperty(packages.get(packageid), property);
	}

	/**
	 * Gets the number of parsed members.
	 * @param packageid ID of the package of those members.
	 * @return Number of members of a package.
	 */
	public int getNumberOfMembers(String packageid) {
		return packages.get(packageid).getMembers().keySet().size();
	}
	
	/**
	 * Gets a property from a member of a package.
	 * @param packageid Package ID of this member.
	 * @param member ID of this member.
	 * @param property Property to be read.
	 * @return Property value.
	 * @throws Exception If something fails.
	 */
	public String getMemberProperty(String packageid, String member,
			String property) throws Exception {
		return BeanUtils.getProperty(packages.get(packageid).getMembers()
				.get(member), property);
	}
	
	/**
	 * Gets the number of parsed stereotype applications.
	 * @return The number of stereotype applications.
	 */
	public int getNumberOfApplications() {
		return applications.size();
	}
	
	/**
	 * Gets a stereotype application property.
	 * 
	 * @param index Index of the stereotype application (index is defined by the parsing order).
	 * @param property Property to be read.
	 * @return Property value.
	 * @throws Exception If something fails.
	 */
	public String getApplicationProperty(int index, String property) throws Exception {
		int i = -1;
		for (StereotypeApplication application : applications) {
			i++;
			if (index == i) {
				return BeanUtils.getProperty(application, property);
			}
		}
		return null;
	}
	
	/**
	 * Validate current model.
	 */
	public void validate() {
		controller.validate(model);
	}
	
	/**
	 * Get the number of validation exceptions.
	 * 
	 * @return Number of validation exceptions.
	 */
	public int getNumberOfValidationExceptions() {
		return model.getValidationExceptions().size();
	}
	
	/**
	 * Gets a validation exception message.
	 * 
	 * @param index Index of the validation exception (index is defined by the parsing order).
	 * 
	 * @return Validation exception message.
	 */
	public String getValidationExceptionMessage(int index) {
		int i = -1;
		for (ValidationException ve : model.getValidationExceptions()) {
			i++;
			if (index == i) {
				return ve.getMessage();
			}
		}
		return null;
	}
	
}