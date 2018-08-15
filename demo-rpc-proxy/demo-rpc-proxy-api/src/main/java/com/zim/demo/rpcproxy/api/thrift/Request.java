/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.zim.demo.rpcproxy.api.thrift;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-08-14")
public class Request implements org.apache.thrift.TBase<Request, Request._Fields>, java.io.Serializable, Cloneable, Comparable<Request> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Request");

  private static final org.apache.thrift.protocol.TField GROUP_FIELD_DESC = new org.apache.thrift.protocol.TField("group", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CLASS_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("class_name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FUNC_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("func_name", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PARAM_FIELD_DESC = new org.apache.thrift.protocol.TField("param", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RequestTupleSchemeFactory());
  }

  public String group; // required
  public String version; // required
  public String class_name; // required
  public String func_name; // required
  public String param; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GROUP((short)1, "group"),
    VERSION((short)2, "version"),
    CLASS_NAME((short)3, "class_name"),
    FUNC_NAME((short)4, "func_name"),
    PARAM((short)5, "param");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // GROUP
          return GROUP;
        case 2: // VERSION
          return VERSION;
        case 3: // CLASS_NAME
          return CLASS_NAME;
        case 4: // FUNC_NAME
          return FUNC_NAME;
        case 5: // PARAM
          return PARAM;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GROUP, new org.apache.thrift.meta_data.FieldMetaData("group", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLASS_NAME, new org.apache.thrift.meta_data.FieldMetaData("class_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FUNC_NAME, new org.apache.thrift.meta_data.FieldMetaData("func_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAM, new org.apache.thrift.meta_data.FieldMetaData("param", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Request.class, metaDataMap);
  }

  public Request() {
  }

  public Request(
    String group,
    String version,
    String class_name,
    String func_name,
    String param)
  {
    this();
    this.group = group;
    this.version = version;
    this.class_name = class_name;
    this.func_name = func_name;
    this.param = param;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Request(Request other) {
    if (other.isSetGroup()) {
      this.group = other.group;
    }
    if (other.isSetVersion()) {
      this.version = other.version;
    }
    if (other.isSetClass_name()) {
      this.class_name = other.class_name;
    }
    if (other.isSetFunc_name()) {
      this.func_name = other.func_name;
    }
    if (other.isSetParam()) {
      this.param = other.param;
    }
  }

  public Request deepCopy() {
    return new Request(this);
  }

  @Override
  public void clear() {
    this.group = null;
    this.version = null;
    this.class_name = null;
    this.func_name = null;
    this.param = null;
  }

  public String getGroup() {
    return this.group;
  }

  public Request setGroup(String group) {
    this.group = group;
    return this;
  }

  public void unsetGroup() {
    this.group = null;
  }

  /** Returns true if field group is set (has been assigned a value) and false otherwise */
  public boolean isSetGroup() {
    return this.group != null;
  }

  public void setGroupIsSet(boolean value) {
    if (!value) {
      this.group = null;
    }
  }

  public String getVersion() {
    return this.version;
  }

  public Request setVersion(String version) {
    this.version = version;
    return this;
  }

  public void unsetVersion() {
    this.version = null;
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return this.version != null;
  }

  public void setVersionIsSet(boolean value) {
    if (!value) {
      this.version = null;
    }
  }

  public String getClass_name() {
    return this.class_name;
  }

  public Request setClass_name(String class_name) {
    this.class_name = class_name;
    return this;
  }

  public void unsetClass_name() {
    this.class_name = null;
  }

  /** Returns true if field class_name is set (has been assigned a value) and false otherwise */
  public boolean isSetClass_name() {
    return this.class_name != null;
  }

  public void setClass_nameIsSet(boolean value) {
    if (!value) {
      this.class_name = null;
    }
  }

  public String getFunc_name() {
    return this.func_name;
  }

  public Request setFunc_name(String func_name) {
    this.func_name = func_name;
    return this;
  }

  public void unsetFunc_name() {
    this.func_name = null;
  }

  /** Returns true if field func_name is set (has been assigned a value) and false otherwise */
  public boolean isSetFunc_name() {
    return this.func_name != null;
  }

  public void setFunc_nameIsSet(boolean value) {
    if (!value) {
      this.func_name = null;
    }
  }

  public String getParam() {
    return this.param;
  }

  public Request setParam(String param) {
    this.param = param;
    return this;
  }

  public void unsetParam() {
    this.param = null;
  }

  /** Returns true if field param is set (has been assigned a value) and false otherwise */
  public boolean isSetParam() {
    return this.param != null;
  }

  public void setParamIsSet(boolean value) {
    if (!value) {
      this.param = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GROUP:
      if (value == null) {
        unsetGroup();
      } else {
        setGroup((String)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((String)value);
      }
      break;

    case CLASS_NAME:
      if (value == null) {
        unsetClass_name();
      } else {
        setClass_name((String)value);
      }
      break;

    case FUNC_NAME:
      if (value == null) {
        unsetFunc_name();
      } else {
        setFunc_name((String)value);
      }
      break;

    case PARAM:
      if (value == null) {
        unsetParam();
      } else {
        setParam((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GROUP:
      return getGroup();

    case VERSION:
      return getVersion();

    case CLASS_NAME:
      return getClass_name();

    case FUNC_NAME:
      return getFunc_name();

    case PARAM:
      return getParam();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GROUP:
      return isSetGroup();
    case VERSION:
      return isSetVersion();
    case CLASS_NAME:
      return isSetClass_name();
    case FUNC_NAME:
      return isSetFunc_name();
    case PARAM:
      return isSetParam();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Request)
      return this.equals((Request)that);
    return false;
  }

  public boolean equals(Request that) {
    if (that == null)
      return false;

    boolean this_present_group = true && this.isSetGroup();
    boolean that_present_group = true && that.isSetGroup();
    if (this_present_group || that_present_group) {
      if (!(this_present_group && that_present_group))
        return false;
      if (!this.group.equals(that.group))
        return false;
    }

    boolean this_present_version = true && this.isSetVersion();
    boolean that_present_version = true && that.isSetVersion();
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (!this.version.equals(that.version))
        return false;
    }

    boolean this_present_class_name = true && this.isSetClass_name();
    boolean that_present_class_name = true && that.isSetClass_name();
    if (this_present_class_name || that_present_class_name) {
      if (!(this_present_class_name && that_present_class_name))
        return false;
      if (!this.class_name.equals(that.class_name))
        return false;
    }

    boolean this_present_func_name = true && this.isSetFunc_name();
    boolean that_present_func_name = true && that.isSetFunc_name();
    if (this_present_func_name || that_present_func_name) {
      if (!(this_present_func_name && that_present_func_name))
        return false;
      if (!this.func_name.equals(that.func_name))
        return false;
    }

    boolean this_present_param = true && this.isSetParam();
    boolean that_present_param = true && that.isSetParam();
    if (this_present_param || that_present_param) {
      if (!(this_present_param && that_present_param))
        return false;
      if (!this.param.equals(that.param))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_group = true && (isSetGroup());
    list.add(present_group);
    if (present_group)
      list.add(group);

    boolean present_version = true && (isSetVersion());
    list.add(present_version);
    if (present_version)
      list.add(version);

    boolean present_class_name = true && (isSetClass_name());
    list.add(present_class_name);
    if (present_class_name)
      list.add(class_name);

    boolean present_func_name = true && (isSetFunc_name());
    list.add(present_func_name);
    if (present_func_name)
      list.add(func_name);

    boolean present_param = true && (isSetParam());
    list.add(present_param);
    if (present_param)
      list.add(param);

    return list.hashCode();
  }

  @Override
  public int compareTo(Request other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGroup()).compareTo(other.isSetGroup());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroup()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.group, other.group);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClass_name()).compareTo(other.isSetClass_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClass_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.class_name, other.class_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFunc_name()).compareTo(other.isSetFunc_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunc_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.func_name, other.func_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParam()).compareTo(other.isSetParam());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParam()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.param, other.param);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Request(");
    boolean first = true;

    sb.append("group:");
    if (this.group == null) {
      sb.append("null");
    } else {
      sb.append(this.group);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("version:");
    if (this.version == null) {
      sb.append("null");
    } else {
      sb.append(this.version);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("class_name:");
    if (this.class_name == null) {
      sb.append("null");
    } else {
      sb.append(this.class_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("func_name:");
    if (this.func_name == null) {
      sb.append("null");
    } else {
      sb.append(this.func_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("param:");
    if (this.param == null) {
      sb.append("null");
    } else {
      sb.append(this.param);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RequestStandardSchemeFactory implements SchemeFactory {
    public RequestStandardScheme getScheme() {
      return new RequestStandardScheme();
    }
  }

  private static class RequestStandardScheme extends StandardScheme<Request> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Request struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // GROUP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.group = iprot.readString();
              struct.setGroupIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.version = iprot.readString();
              struct.setVersionIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLASS_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.class_name = iprot.readString();
              struct.setClass_nameIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FUNC_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.func_name = iprot.readString();
              struct.setFunc_nameIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARAM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.param = iprot.readString();
              struct.setParamIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Request struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.group != null) {
        oprot.writeFieldBegin(GROUP_FIELD_DESC);
        oprot.writeString(struct.group);
        oprot.writeFieldEnd();
      }
      if (struct.version != null) {
        oprot.writeFieldBegin(VERSION_FIELD_DESC);
        oprot.writeString(struct.version);
        oprot.writeFieldEnd();
      }
      if (struct.class_name != null) {
        oprot.writeFieldBegin(CLASS_NAME_FIELD_DESC);
        oprot.writeString(struct.class_name);
        oprot.writeFieldEnd();
      }
      if (struct.func_name != null) {
        oprot.writeFieldBegin(FUNC_NAME_FIELD_DESC);
        oprot.writeString(struct.func_name);
        oprot.writeFieldEnd();
      }
      if (struct.param != null) {
        oprot.writeFieldBegin(PARAM_FIELD_DESC);
        oprot.writeString(struct.param);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RequestTupleSchemeFactory implements SchemeFactory {
    public RequestTupleScheme getScheme() {
      return new RequestTupleScheme();
    }
  }

  private static class RequestTupleScheme extends TupleScheme<Request> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Request struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGroup()) {
        optionals.set(0);
      }
      if (struct.isSetVersion()) {
        optionals.set(1);
      }
      if (struct.isSetClass_name()) {
        optionals.set(2);
      }
      if (struct.isSetFunc_name()) {
        optionals.set(3);
      }
      if (struct.isSetParam()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetGroup()) {
        oprot.writeString(struct.group);
      }
      if (struct.isSetVersion()) {
        oprot.writeString(struct.version);
      }
      if (struct.isSetClass_name()) {
        oprot.writeString(struct.class_name);
      }
      if (struct.isSetFunc_name()) {
        oprot.writeString(struct.func_name);
      }
      if (struct.isSetParam()) {
        oprot.writeString(struct.param);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Request struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.group = iprot.readString();
        struct.setGroupIsSet(true);
      }
      if (incoming.get(1)) {
        struct.version = iprot.readString();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.class_name = iprot.readString();
        struct.setClass_nameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.func_name = iprot.readString();
        struct.setFunc_nameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.param = iprot.readString();
        struct.setParamIsSet(true);
      }
    }
  }

}
