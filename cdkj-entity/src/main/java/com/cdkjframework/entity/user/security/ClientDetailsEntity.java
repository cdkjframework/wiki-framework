package com.cdkjframework.entity.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user.security
 * @ClassName: ClientDetailsEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ClientDetailsEntity implements ClientDetails {

    private String clientId;
    private String secret;
    private String role;
    private Set<String> scope;
    private Set<String> types;
    private Set<String> autoApproveScopes;
    private Set<String> resourceIds;
    private Map<String, Object> additionalInformation;
    private Set<String> registeredRedirectUris;
    private List<GrantedAuthority> authorities;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * The client id.
     *
     * @return The client id.
     */
    @Override
    public String getClientId() {
        return null;
    }

    /**
     * The resources that this client can access. Can be ignored by callers if empty.
     *
     * @return The resources of this client.
     */
    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    /**
     * Whether a secret is required to authenticate this client.
     *
     * @return Whether a secret is required to authenticate this client.
     */
    @Override
    public boolean isSecretRequired() {
        return false;
    }

    /**
     * The client secret. Ignored if the {@link #isSecretRequired() secret isn't required}.
     *
     * @return The client secret.
     */
    @Override
    public String getClientSecret() {
        return null;
    }

    /**
     * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
     * ignored.
     *
     * @return Whether this client is limited to a specific scope.
     */
    @Override
    public boolean isScoped() {
        return false;
    }

    /**
     * The scope of this client. Empty if the client isn't scoped.
     *
     * @return The scope of this client.
     */
    @Override
    public Set<String> getScope() {
        return null;
    }

    /**
     * The grant types for which this client is authorized.
     *
     * @return The grant types for which this client is authorized.
     */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return null;
    }

    /**
     * The pre-defined redirect URI for this client to use during the "authorization_code" access grant. See OAuth spec,
     * section 4.1.1.
     *
     * @return The pre-defined redirect URI for this client.
     */
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    /**
     * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>.
     * Note that these are NOT the authorities that are granted to the user with an authorized access token.
     * Instead, these authorities are inherent to the client itself.
     *
     * @return the authorities (never <code>null</code>)
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * The access token validity period for this client. Null if not set explicitly (implementations might use that fact
     * to provide a default value for instance).
     *
     * @return the access token validity period
     */
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return null;
    }

    /**
     * The refresh token validity period for this client. Null for default value set by token service, and
     * zero or negative for non-expiring tokens.
     *
     * @return the refresh token validity period
     */
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    /**
     * Test whether client needs user approval for a particular scope.
     *
     * @param scope the scope to consider
     * @return true if this client does not need user approval
     */
    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    /**
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
     * for storing descriptive information.
     *
     * @return a map of additional information
     */
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
